package com.github.shepherdviolet.webdemo.demo.rocketmq.producer;

import com.github.shepherdviolet.glacimon.java.concurrent.ThreadPoolExecutorUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

/**
 * 事务型消息生产者示例
 *
 * @author S.Violet
 */
@Component
public class RocketMqTransactionalProducer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private TransactionMQProducer producer;

    @PostConstruct
    public void init() throws MQClientException {
        producer = new TransactionMQProducer("please_rename_unique_group_name");
        producer.setExecutorService(ThreadPoolExecutorUtils.createFixed(5, "Transaction-listener-%d"));
        producer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                /*
                 * 程序会先向MQ发送一个HalfMessage, 然后执行该方法处理本地事务,
                 * 当本地事务结果未明或发生中止, MQ会调用下面的checkLocalTransaction来检查状态
                 */
                logger.info("RocketMqTransactionalProducer local transaction");

                //本地事务处理成功, 提交消息
//                return LocalTransactionState.COMMIT_MESSAGE;
                //本地事务处理失败, 回滚消息
//                return LocalTransactionState.ROLLBACK_MESSAGE;
                //本地事务结果未明, MQ后续会调用下面的checkLocalTransaction来检查状态
                return LocalTransactionState.UNKNOW;
            }
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                /*
                 * 当MQ发现一个HalfMessage状态不明的时候, 会调用该方法来检查事务处理的结果
                 */
                logger.info("RocketMqTransactionalProducer check transaction state");

                //本地事务处理成功, 提交消息
                return LocalTransactionState.COMMIT_MESSAGE;
                //本地事务处理失败, 回滚消息
//                return LocalTransactionState.ROLLBACK_MESSAGE;
                //本地事务结果未明, MQ后续会调用下面的checkLocalTransaction来检查状态
//                return LocalTransactionState.UNKNOW;
            }
        });
        producer.start();
    }

    public TransactionSendResult sendInTransaction(Message msg, Object arg) throws MQClientException {
        return producer.sendMessageInTransaction(msg, arg);
    }

    @PreDestroy
    public void destroy(){
        try {
            producer.shutdown();
        } catch (Exception ignore) {
        }
    }

}
