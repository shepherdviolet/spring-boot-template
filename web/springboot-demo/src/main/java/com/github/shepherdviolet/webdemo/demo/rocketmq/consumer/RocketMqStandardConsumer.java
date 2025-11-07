package com.github.shepherdviolet.webdemo.demo.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.nio.charset.StandardCharsets;

/**
 * 原生的消费者示例
 *
 * @author S.Violet
 */
@Component
public class RocketMqStandardConsumer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${glacispring.helper.rocketmq.namesrv:localhost:9876}")
    private String nameServer;

    private DefaultMQPushConsumer simpleConsumer;
    private DefaultMQPushConsumer orderedConsumer;
    private DefaultMQPushConsumer broadcastConsumer;
    private DefaultMQPushConsumer filterConsumer;

    @PostConstruct
    public void init() throws MQClientException {
        initSimple();
        initOrdered();
        initBroadcast();
        initFilter();
    }

    /**
     * 普通并发消费
     */
    private void initSimple() throws MQClientException {
        //defaultConsumerGroup消费组名
        simpleConsumer = new DefaultMQPushConsumer("simple-consumer");
        //NameServer地址, IP:PORT;IP:PORT
        simpleConsumer.setNamesrvAddr(nameServer);
        //订阅Topic为simple, 任意TAG的消息
        simpleConsumer.subscribe("simple", "*");
        //从队列头部取消息
        simpleConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //批量消费消息, 默认1
//        simpleConsumer.setConsumeMessageBatchMaxSize(10);
        //并发消费
        simpleConsumer.registerMessageListener((MessageListenerConcurrently) (messages, context) -> {
            logger.info("Receive New Messages: {}", messages);
            //部分退回机制: 先设置ackIndex为-1
//            context.setAckIndex(-1);
            //处理收到的消息(默认只有一条消息, 除非设置了setConsumeMessageBatchMaxSize)
            for (MessageExt message : messages) {
                logger.info("Body: {}", new String(message.getBody(), StandardCharsets.UTF_8));
//                if (succeed) {
//                    //部分退回机制: 消费成功+1
//                    context.setAckIndex(context.getAckIndex() + 1);
//                } else {
//                    //部分退回机制: 消费失败终止循环
//                    break;
//                }
            }
            //消费成功(如果部分退回, 也要返回CONSUME_SUCCESS)
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            //消费失败, 退回全部消息, 消息会在数秒钟之后重新被消费(中途会消费其他消息), 失败次数达到上限后会进入MQ死信队列
//            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        });
        //启动
        simpleConsumer.start();
    }

    /**
     * 顺序消费. 全局单线程消费, 到达消费者的延迟很大(几十秒级).
     * 顺序消费有两个前提, 生产者必须将消息放进同一个队列(可以将某个TAG的消息放进同一个队列), 消费者必须消费同一个队列(订阅那个TAG).
     * 消费者如果是集群, 队列会被全局锁定(同时只有一个消费者可以消费消息), 每个消费者同时只有一个线程在消费消息.
     *
     * autoCommit=true时: 如果消息处理完成, 返回SUCCESS, 客户端会将消息Offset提交给Broker, 这条消息将不会再被其他消费者消费;
     * 如果消息处理失败(且希望被重新处理), 返回SUSPEND_CURRENT_QUEUE_A_MOMENT, 这条消息会很快被重新处理. 相当于每一次的消息
     * 处理都是一个独立的事务, 要么成功, 要么失败.
     *
     * autoCommit=false时: 如果消息处理完成, 返回SUCCESS, 客户端不会将Offset提交给Broker, 直到有个消息返回COMMIT, 客户端才会
     * 将Offset提交给Broker(告知Broker这一系列消息已被消费掉). 相当于COMMIT前的一系列消息处理是一个整体的事务, 全部处理完才会
     * 提交给Broker. 如果COMMIT前进程挂掉, 这一系列的消息会被其他消费者重新处理. 另外, 还可以返回ROLLBACK, 之前未提交的消息会
     * 重新被消费者处理.
     */
    private void initOrdered() throws MQClientException {
        orderedConsumer = new DefaultMQPushConsumer("ordered-consumer");
        orderedConsumer.setNamesrvAddr(nameServer);
        orderedConsumer.subscribe("ordered", "A");
        //第一次从头部开始消费, 后续从上次消费的位置继续消费
        orderedConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        orderedConsumer.registerMessageListener((MessageListenerOrderly) (messages, context) -> {
            //默认true, 设置为true时, SUCCESS/COMMIT/ROLLBACK时都会将消息OFFSET提交到本地队列和Broker
            //设置为false时, 必须返回COMMIT才会将消息OFFSET提交到本地队列和Broker
            context.setAutoCommit(true);
            logger.info("Receive New Ordered Messages: {}", messages);
            for (MessageExt message : messages) {
                logger.info("Body: {}", new String(message.getBody(), StandardCharsets.UTF_8));
            }
            //消费成功
            return ConsumeOrderlyStatus.SUCCESS;
            //消费失败, 消息会很快被重新消费(还是这一条), 且会无限重复, 需要自行处理无限循环的情况
//            return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
            //[配合autoCommit=false使用] 消费成功并提交OFFSET给Broker(告知Broker这一系列消息已被消费掉)
//            return ConsumeOrderlyStatus.COMMIT;
            //[配合autoCommit=false使用] 回滚, 之前未提交的消息会重新按顺序被消费者消费一次
//            return ConsumeOrderlyStatus.ROLLBACK;
        });
        orderedConsumer.start();
    }

    /**
     * 广播消费(广播模式只与消费者有关, 生产者无关)
     */
    private void initBroadcast() throws MQClientException {
        broadcastConsumer = new DefaultMQPushConsumer("broadcast-consumer");
        broadcastConsumer.setNamesrvAddr(nameServer);
        //订阅Topic为broadcast, TAG为A/B/C的消息
        broadcastConsumer.subscribe("broadcast", "A || B || C");
        //广播模式消费(订阅该消息的消费者都能消费一次)
        broadcastConsumer.setMessageModel(MessageModel.BROADCASTING);
        broadcastConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        broadcastConsumer.registerMessageListener((MessageListenerConcurrently) (messages, context) -> {
            logger.info("Receive New Broadcast Messages: {}", messages);
            for (MessageExt message : messages) {
                logger.info("Body: {}", new String(message.getBody(), StandardCharsets.UTF_8));
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        broadcastConsumer.start();
    }

    /**
     * 参数过滤消息
     */
    private void initFilter() throws MQClientException {
        filterConsumer = new DefaultMQPushConsumer("filter-consumer");
        filterConsumer.setNamesrvAddr(nameServer);
        //订阅Topic为filter, a值在0-3之间的消息
        filterConsumer.subscribe("filter", MessageSelector.bySql("a between 0 and 3"));
        filterConsumer.registerMessageListener((MessageListenerConcurrently) (messages, context) -> {
            logger.info("Receive New Filter Messages: {}", messages);
            for (MessageExt message : messages) {
                logger.info("Body: {}", new String(message.getBody(), StandardCharsets.UTF_8));
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        filterConsumer.start();
    }

    @PreDestroy
    public void destroy() {
        try {
            //停止
            simpleConsumer.shutdown();
            orderedConsumer.shutdown();
            broadcastConsumer.shutdown();
            filterConsumer.shutdown();
        } catch (Exception ignore) {
        }
    }

}
