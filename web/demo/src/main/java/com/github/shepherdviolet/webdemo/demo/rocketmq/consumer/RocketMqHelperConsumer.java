package com.github.shepherdviolet.webdemo.demo.rocketmq.consumer;

import com.github.shepherdviolet.glacimon.spring.helper.rocketmq.consumer.RocketMqConcurrentConsumer;
import com.github.shepherdviolet.glacimon.spring.helper.rocketmq.consumer.RocketMqCustomConsumer;
import com.github.shepherdviolet.glacimon.spring.helper.rocketmq.consumer.RocketMqOrderedConsumer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GlacispringRocketMQHelper消费者示例
 *
 * @author S.Violet
 */
@Component
public class RocketMqHelperConsumer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 普通消费者: 测试MethodInvoker
     */
    @RocketMqConcurrentConsumer(
            consumerGroup = "glacispringInvokeTest1",
            topic = "glacispringInvokeTest",
            subExpression = "1"
    )
    public boolean glacispringInvokeTest1(MessageExt message){
        logger.info("Received MessageExt: {}", message);
        return true;
    }

    /**
     * 普通消费者: 测试MethodInvoker
     */
    @RocketMqConcurrentConsumer(
            consumerGroup = "glacispringInvokeTest2",
            topic = "glacispringInvokeTest",
            subExpression = "2"
    )
    public void glacispringInvokeTest2(Message message){
        logger.info("Received Message: {}", message);
    }

    /**
     * 普通消费者: 测试MethodInvoker
     */
    @RocketMqConcurrentConsumer(
            consumerGroup = "glacispringInvokeTest3",
            topic = "glacispringInvokeTest",
            subExpression = "3"
    )
    public void glacispringInvokeTest3(byte[] message){
        logger.info("Received byte[]: {}", new String(message, StandardCharsets.UTF_8));
    }

    /**
     * 普通消费者: 测试MethodInvoker
     */
    @RocketMqConcurrentConsumer(
            consumerGroup = "glacispringInvokeTest4",
            topic = "glacispringInvokeTest",
            subExpression = "4"
    )
    public void glacispringInvokeTest4(String message){
        logger.info("Received String: {}", message);
    }

    /**
     * 普通消费者: 测试MethodInvoker
     */
    @RocketMqConcurrentConsumer(
            consumerGroup = "glacispringInvokeTest5",
            topic = "glacispringInvokeTest",
            subExpression = "5"
    )
    public void glacispringInvokeTest4(Map<String, Object> map){
        logger.info("Received Map: {}", map);
    }

    /**
     * 广播消费者
     */
    @RocketMqConcurrentConsumer(
            consumerGroup = "glacispringBroadcastTest1",
            topic = "glacispringBroadcastTest",
            isBroadcast = true
    )
    public void glacispringBroadcastTest1(String message){
        logger.info("glacispringBroadcastTest1: Received String 1: {}", message);
    }

    /**
     * 广播消费者
     */
    @RocketMqConcurrentConsumer(
            consumerGroup = "glacispringBroadcastTest2",
            topic = "glacispringBroadcastTest",
            isBroadcast = true
    )
    public void glacispringBroadcastTest2(String message){
        logger.info("glacispringBroadcastTest2: Received String 2: {}", message);
    }

    private AtomicInteger failedCounter1 = new AtomicInteger(0);

    /**
     * 消费失败测试
     */
    @RocketMqConcurrentConsumer(
            consumerGroup = "glacispringReconsumeTest1",
            topic = "glacispringReconsumeTest",
            subExpression = "1"
    )
    public boolean glacispringReconsumeTest1(String message){
        boolean success = failedCounter1.getAndIncrement() % 2 == 1;
        logger.info("glacispringReconsumeTest1: Received String " + success + ": {}", message);
        //返回false表示消费失败
        return success;
    }

    private AtomicInteger failedCounter2 = new AtomicInteger(0);

    /**
     * 消费失败测试
     */
    @RocketMqConcurrentConsumer(
            consumerGroup = "glacispringReconsumeTest2",
            topic = "glacispringReconsumeTest",
            subExpression = "2"
    )
    public void glacispringReconsumeTest2(String message){
        boolean success = failedCounter2.getAndIncrement() % 2 == 1;
        logger.info("glacispringReconsumeTest2: Received String " + success + ": {}", message);
        //默认情况下抛出异常不会消费失败
        if (!success) {
            throw new RuntimeException("TEST EXCEPTION");
        }
    }

    private AtomicInteger failedCounter3 = new AtomicInteger(0);

    /**
     * 消费失败测试
     */
    @RocketMqConcurrentConsumer(
            consumerGroup = "glacispringReconsumeTest3",
            topic = "glacispringReconsumeTest",
            reconsumeWhenException = true,
            subExpression = "3"
    )
    public void glacispringReconsumeTest3(String message){
        boolean success = failedCounter3.getAndIncrement() % 2 == 1;
        logger.info("glacispringReconsumeTest3: Received String " + success + ": {}", message);
        //设置了reconsumeWhenException = true后, 抛出异常会导致消费失败
        if (!success) {
            throw new RuntimeException("TEST EXCEPTION");
        }
    }

    /**
     * SQL过滤
     */
    @RocketMqConcurrentConsumer(
            consumerGroup = "glacispringFilterTest1",
            topic = "glacispringFilterTest",
            sqlExpression = "a between 0 and 3"
    )
    public void glacispringFilterTest(String message) {
        logger.info("glacispringFilterTest: Received String 1: {}", message);
    }

    /**
     * 将自定义的DefaultMQPushConsumer绑定到这个方法上
     */
    @RocketMqCustomConsumer(
            consumerBeanName = "customConsumer",
            isOrdered = false
    )
    public void glacispringCustomConsumerTest(String message) {
        logger.info("glacispringCustomConsumerTest: Received String 1: {}", message);
    }

    /**
     * 顺序消费测试
     */
    @RocketMqOrderedConsumer(
            consumerGroup = "glacispringOrderedTest1",
            topic = "glacispringOrderedTest",
            subExpression = "1"
    )
    public void glacispringOrderedTest1(String message){
        logger.info("glacispringOrderedTest1: Received String 1: {}", message);
    }

}
