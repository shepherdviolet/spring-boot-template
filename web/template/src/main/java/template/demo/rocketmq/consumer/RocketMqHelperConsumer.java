package template.demo.rocketmq.consumer;

import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sviolet.slate.common.helper.rocketmq.consumer.RocketMqConcurrentConsumer;
import sviolet.slate.common.helper.rocketmq.consumer.RocketMqCustomConsumer;
import sviolet.slate.common.helper.rocketmq.consumer.RocketMqOrderedConsumer;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SlateRocketMQHelper消费者示例
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
            consumerGroup = "slateInvokeTest1",
            topic = "slateInvokeTest",
            subExpression = "1"
    )
    public boolean slateInvokeTest1(MessageExt message){
        logger.info("Received MessageExt: {}", message);
        return true;
    }

    /**
     * 普通消费者: 测试MethodInvoker
     */
    @RocketMqConcurrentConsumer(
            consumerGroup = "slateInvokeTest2",
            topic = "slateInvokeTest",
            subExpression = "2"
    )
    public void slateInvokeTest2(Message message){
        logger.info("Received Message: {}", message);
    }

    /**
     * 普通消费者: 测试MethodInvoker
     */
    @RocketMqConcurrentConsumer(
            consumerGroup = "slateInvokeTest3",
            topic = "slateInvokeTest",
            subExpression = "3"
    )
    public void slateInvokeTest3(byte[] message){
        logger.info("Received byte[]: {}", new String(message, StandardCharsets.UTF_8));
    }

    /**
     * 普通消费者: 测试MethodInvoker
     */
    @RocketMqConcurrentConsumer(
            consumerGroup = "slateInvokeTest4",
            topic = "slateInvokeTest",
            subExpression = "4"
    )
    public void slateInvokeTest4(String message){
        logger.info("Received String: {}", message);
    }

    /**
     * 普通消费者: 测试MethodInvoker
     */
    @RocketMqConcurrentConsumer(
            consumerGroup = "slateInvokeTest5",
            topic = "slateInvokeTest",
            subExpression = "5"
    )
    public void slateInvokeTest4(Map<String, Object> map){
        logger.info("Received Map: {}", map);
    }

    /**
     * 广播消费者
     */
    @RocketMqConcurrentConsumer(
            consumerGroup = "slateBroadcastTest1",
            topic = "slateBroadcastTest",
            isBroadcast = true
    )
    public void slateBroadcastTest1(String message){
        logger.info("slateBroadcastTest1: Received String 1: {}", message);
    }

    /**
     * 广播消费者
     */
    @RocketMqConcurrentConsumer(
            consumerGroup = "slateBroadcastTest2",
            topic = "slateBroadcastTest",
            isBroadcast = true
    )
    public void slateBroadcastTest2(String message){
        logger.info("slateBroadcastTest2: Received String 2: {}", message);
    }

    private AtomicInteger failedCounter1 = new AtomicInteger(0);

    /**
     * 消费失败测试
     */
    @RocketMqConcurrentConsumer(
            consumerGroup = "slateReconsumeTest1",
            topic = "slateReconsumeTest",
            subExpression = "1"
    )
    public boolean slateReconsumeTest1(String message){
        boolean success = failedCounter1.getAndIncrement() % 2 == 1;
        logger.info("slateReconsumeTest1: Received String " + success + ": {}", message);
        //返回false表示消费失败
        return success;
    }

    private AtomicInteger failedCounter2 = new AtomicInteger(0);

    /**
     * 消费失败测试
     */
    @RocketMqConcurrentConsumer(
            consumerGroup = "slateReconsumeTest2",
            topic = "slateReconsumeTest",
            subExpression = "2"
    )
    public void slateReconsumeTest2(String message){
        boolean success = failedCounter2.getAndIncrement() % 2 == 1;
        logger.info("slateReconsumeTest2: Received String " + success + ": {}", message);
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
            consumerGroup = "slateReconsumeTest3",
            topic = "slateReconsumeTest",
            reconsumeWhenException = true,
            subExpression = "3"
    )
    public void slateReconsumeTest3(String message){
        boolean success = failedCounter3.getAndIncrement() % 2 == 1;
        logger.info("slateReconsumeTest3: Received String " + success + ": {}", message);
        //设置了reconsumeWhenException = true后, 抛出异常会导致消费失败
        if (!success) {
            throw new RuntimeException("TEST EXCEPTION");
        }
    }

    /**
     * SQL过滤
     */
    @RocketMqConcurrentConsumer(
            consumerGroup = "slateFilterTest1",
            topic = "slateFilterTest",
            sqlExpression = "a between 0 and 3"
    )
    public void slateFilterTest(String message) {
        logger.info("slateFilterTest: Received String 1: {}", message);
    }

    /**
     * 将自定义的DefaultMQPushConsumer绑定到这个方法上
     */
    @RocketMqCustomConsumer(
            consumerBeanName = "customConsumer",
            isOrdered = false
    )
    public void slateCustomConsumerTest(String message) {
        logger.info("slateCustomConsumerTest: Received String 1: {}", message);
    }

    /**
     * 顺序消费测试
     */
    @RocketMqOrderedConsumer(
            consumerGroup = "slateOrderedTest1",
            topic = "slateOrderedTest",
            subExpression = "1"
    )
    public void slateOrderedTest1(String message){
        logger.info("slateOrderedTest1: Received String 1: {}", message);
    }

}
