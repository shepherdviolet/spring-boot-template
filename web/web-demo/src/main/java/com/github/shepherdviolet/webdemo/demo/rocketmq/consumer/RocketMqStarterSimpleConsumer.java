//package com.github.shepherdviolet.webdemo.demo.rocketmq.consumer;
//
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
///**
// * rocketmq-spring-boot-starter消费者示例: 简单消费者.
// * https://github.com/apache/rocketmq-spring
// * 注意: 要用rocketmq-spring-boot-starter的话, 就不要自己创建DefaultMQProducer了, 要创建也不要start()
// *
// * @author S.Violet
// */
//@Component
//@RocketMQMessageListener(
//        topic = "starterSimple",
//        consumerGroup = "starterConsumer",
//        consumeThreadMax = 5
//)
//public class RocketMqStarterSimpleConsumer implements RocketMQListener<String> {
//
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Override
//    public void onMessage(String message) {
//        logger.info("RocketMqStarterSimpleConsumer received:" + message);
////        throw new RuntimeException("FAIL");//抛出异常时退回消息
//    }
//
//}
