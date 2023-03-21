package com.github.shepherdviolet.webdemo.demo.rocketmq.controller;

import com.github.shepherdviolet.glacimon.spring.helper.rocketmq.producer.RocketMqMessageSerializer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.shepherdviolet.webdemo.demo.rocketmq.producer.RocketMqTransactionalProducer;
import com.github.shepherdviolet.webdemo.demo.rocketmq.utils.MessageListSplitter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * RocketMQ示例
 *
 * @author S.Violet
 */
@RestController
@RequestMapping("/rocketmq")
public class RocketMqController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("defaultProducer")
    private DefaultMQProducer defaultProducer;

    @Autowired
    private RocketMqTransactionalProducer transactionalProducer;

    /**
     * 普通并发消息同步发送
     * GET
     * http://localhost:8000/rocketmq/simple-sync
     */
    @RequestMapping(value = "/simple-sync")
    public String simpleSync() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Message message = new Message();
        message.setTopic("simple");
        message.setTags("A");
        message.setBody("hello consumer".getBytes(StandardCharsets.UTF_8));
        //实际应正确处理异常
        SendResult result = defaultProducer.send(message);
        return String.valueOf(result);
    }

    private AtomicInteger messageCount = new AtomicInteger(0);

    /**
     * 顺序消息同步发送
     * GET
     * http://localhost:8000/rocketmq/ordered-sync
     */
    @RequestMapping(value = "/ordered-sync")
    public String orderedSync() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        for (int i = 0 ; i < 10 ; i++) {
            Message message = new Message();
            message.setTopic("ordered");
            //指定TAG为A
            message.setTags("A");
            message.setBody(("hello ordered " + messageCount.getAndIncrement()).getBytes(StandardCharsets.UTF_8));
            //第一个参数是消息
            //第二个参数是队列选择逻辑(根据arg选择用哪个队列)
            //第三个参数时arg, 即lambda表达式的第三个参数
            //这里指定用0号队列, 消费者订阅TAG=A的话就相当于固定从0号队列取消息了
            //MQ只保证同一个队列的消息的顺序
            defaultProducer.send(message, (mqs, msg, arg) -> mqs.get(((int) arg) % mqs.size()), 0);
        }
        return "ok";
    }

    /**
     * 广播消息同步发送
     * GET
     * http://localhost:8000/rocketmq/broadcast-sync
     */
    @RequestMapping(value = "/broadcast-sync")
    public String broadcastSync() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Message message = new Message();
        message.setTopic("broadcast");
        message.setTags("A");
        message.setBody("hello broadcast".getBytes(StandardCharsets.UTF_8));
        //实际应正确处理异常
        SendResult result = defaultProducer.send(message);
        return String.valueOf(result);
    }

    /**
     * 定时消息同步发送
     * GET
     * http://localhost:8000/rocketmq/schedule-sync
     */
    @RequestMapping(value = "/schedule-sync")
    public String scheduleSync() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Message message = new Message();
        message.setTopic("simple");
        message.setTags("A");
        message.setBody("hello scheduled".getBytes(StandardCharsets.UTF_8));
        // 0不延迟, 其他级别对应Broker的配置, 默认:messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        message.setDelayTimeLevel(3);
        //实际应正确处理异常
        SendResult result = defaultProducer.send(message);
        return String.valueOf(result);
    }

    /**
     * 批量消息同步发送
     * GET
     * http://localhost:8000/rocketmq/batch-sync
     */
    @RequestMapping(value = "/batch-sync")
    public String batchSync() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        //Messages of the same batch should have: same topic, same waitStoreMsgOK and no schedule support.
        String topic = "simple";
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(topic, "A", "Hello batch 1".getBytes()));
        messages.add(new Message(topic, "B", "Hello batch 2".getBytes()));
        messages.add(new Message(topic, "C", "Hello batch 3".getBytes()));
        //每一批消息不得超过1M, 因此消息多的时候要分批
        MessageListSplitter splitter = new MessageListSplitter(messages);
        while (splitter.hasNext()) {
            List<Message> listItem = splitter.next();
            defaultProducer.send(listItem);
        }
        return "ok";
    }

    /**
     * 参数过滤消息同步发送, 需要Broker配置enablePropertyFilter=true
     * GET
     * http://localhost:8000/rocketmq/filter-sync
     */
    @RequestMapping(value = "/filter-sync")
    public String filterSync() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Message message = new Message();
        message.setTopic("filter");
        message.setTags("A");
        //过滤参数
        message.putUserProperty("a", "3");
        message.setBody("hello filter".getBytes(StandardCharsets.UTF_8));
        SendResult result = defaultProducer.send(message);
        return String.valueOf(result);
    }

    /**
     * 事务消息发送
     * GET
     * http://localhost:8000/rocketmq/transactional-sync
     *
     * 过程
     * RocketMqTransactionalProducer local transaction -> 等待分钟级 (因为本地事务返回了UNKNOW)
     * RocketMqTransactionalProducer check transaction state -> MQ来检查状态
     * Body: hello transactional -> 最终收到消息
     */
    @RequestMapping(value = "/transactional-sync")
    public String transactionalSync() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Message message = new Message();
        message.setTopic("simple");
        message.setTags("A");
        message.setBody("hello transactional".getBytes(StandardCharsets.UTF_8));
        /*
         * 程序会先向MQ发送一个HalfMessage, 然后执行transactionalProducer里编写的本地事务,
         * 当本地事务结果未明或发生中止, MQ会调用checkLocalTransaction来检查状态
         */
        SendResult result = transactionalProducer.sendInTransaction(message, null);
        return String.valueOf(result);
    }

    /* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

    /**
     * Glacispring RocketMQHelper Test: Invoker test
     * GET
     * http://localhost:8000/rocketmq/helper/invoker
     */
    @RequestMapping(value = "/helper/invoker")
    public String helperInvoker() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        defaultProducer.send(new Message("glacispringInvokeTest", "1", "hello invoke 1".getBytes(StandardCharsets.UTF_8)));
        defaultProducer.send(new Message("glacispringInvokeTest", "2", "hello invoke 2".getBytes(StandardCharsets.UTF_8)));
        defaultProducer.send(new Message("glacispringInvokeTest", "3", "hello invoke 3".getBytes(StandardCharsets.UTF_8)));
        defaultProducer.send(new Message("glacispringInvokeTest", "4", "hello invoke 4".getBytes(StandardCharsets.UTF_8)));

        Map<String, Object> map = new HashMap<>(16);
        map.put("name", "halo");
        Map<String, Object> subMap = new HashMap<>(16);
        subMap.put("id", "123465");
        map.put("map", subMap);
        defaultProducer.send(new Message("glacispringInvokeTest", "5", RocketMqMessageSerializer.serialize(map)));

        return "ok";
    }

    /**
     * Glacispring RocketMQHelper Test: Broadcast test
     * GET
     * http://localhost:8000/rocketmq/helper/broadcast
     */
    @RequestMapping(value = "/helper/broadcast")
    public String helperBroadcast() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        defaultProducer.send(new Message("glacispringBroadcastTest", "1", "hello broadcast".getBytes(StandardCharsets.UTF_8)));
        return "ok";
    }

    /**
     * Glacispring RocketMQHelper Test: Reconsume test
     * GET
     * http://localhost:8000/rocketmq/helper/reconsume
     */
    @RequestMapping(value = "/helper/reconsume")
    public String helperReconsume() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        defaultProducer.send(new Message("glacispringReconsumeTest", "1", "hello reconsume 1".getBytes(StandardCharsets.UTF_8)));
        defaultProducer.send(new Message("glacispringReconsumeTest", "2", "hello reconsume 2".getBytes(StandardCharsets.UTF_8)));
        defaultProducer.send(new Message("glacispringReconsumeTest", "3", "hello reconsume 3".getBytes(StandardCharsets.UTF_8)));
        return "ok";
    }

    /**
     * Glacispring RocketMQHelper Test: Sql filter test
     * GET
     * http://localhost:8000/rocketmq/helper/filter
     */
    @RequestMapping(value = "/helper/filter")
    public String helperFilter() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Message message = new Message("glacispringFilterTest", "1", "hello filter 1".getBytes(StandardCharsets.UTF_8));
        message.putUserProperty("a", "2");
        defaultProducer.send(message);
        return "ok";
    }

    /**
     * Glacispring RocketMQHelper Test: Custom consumer test
     * GET
     * http://localhost:8000/rocketmq/helper/custom
     */
    @RequestMapping(value = "/helper/custom")
    public String helperCustom() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        defaultProducer.send(new Message("glacispringCustomConsumerTest", "1", "hello custom".getBytes(StandardCharsets.UTF_8)));
        return "ok";
    }

    /**
     * Glacispring RocketMQHelper Test: Ordered message test
     * GET
     * http://localhost:8000/rocketmq/helper/ordered
     */
    @RequestMapping(value = "/helper/ordered")
    public String helperOrdered() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        for (int i = 0 ; i < 10 ; i++) {
            Message message = new Message();
            message.setTopic("glacispringOrderedTest");
            //指定TAG为1
            message.setTags("1");
            message.setBody(("hello ordered " + messageCount.getAndIncrement()).getBytes(StandardCharsets.UTF_8));
            //第一个参数是消息
            //第二个参数是队列选择逻辑(根据arg选择用哪个队列)
            //第三个参数时arg, 即lambda表达式的第三个参数
            //这里指定用0号队列, 消费者订阅TAG=A的话就相当于固定从0号队列取消息了
            //MQ只保证同一个队列的消息的顺序
            defaultProducer.send(message, (mqs, msg, arg) -> mqs.get(((int) arg) % mqs.size()), 0);
        }
        return "ok";
    }

    /* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

//    @Autowired
//    private RocketMQTemplate rocketMqTemplate;
//
//    /**
//     * rocketmq-spring-boot-starter Test: simple
//     * GET
//     * http://localhost:8000/rocketmq/starter/simple
//     * 注意: 要用rocketmq-spring-boot-starter的话, 就不要自己创建DefaultMQProducer了, 要创建也不要start()
//     */
//    @RequestMapping(value = "/starter/simple")
//    public String starterSimple(){
//        rocketMqTemplate.send("starterSimple", MessageBuilder.withPayload("starterSimple data").build());
////        rocketMqTemplate.convertAndSend("starterSimple", "starterSimple data");
////        rocketMqTemplate.convertAndSend("starterSimple", new MyBean());//MyBean必须实现Serializable
//        return "ok";
//    }

}
