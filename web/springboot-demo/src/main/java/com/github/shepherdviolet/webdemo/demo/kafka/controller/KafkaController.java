package com.github.shepherdviolet.webdemo.demo.kafka.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Kafka示例
 */
@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private static final Logger logger = LoggerFactory.getLogger(KafkaController.class);

    @Autowired
    private KafkaTemplate<String, String> template;

    /**
     * http://localhost:8000/kafka
     * http://localhost:8000/kafka/test1
     *
     */
    @RequestMapping({"", "/test1"})
    public String test1() {
        template.send("topic-1", "hello listener 1!"); // Topic要在KafkaConfiguration里创建
        return "sent";
    }

    @KafkaListener(
            id = "id-1", // 这个id同一个进程内不能重复
            topics = "topic-1"
    )
    public void onTopic1Received(String message) {
        logger.info("KafkaController received message from kafka (topic-1): " + message);
        // TODO 注意: 如果这里抛出异常, 会触发Kafka消息重新消费(相同的消息重新在这里被消费, 如果一直抛出异常, 达到一定次数后会停止, 变成死信)
    }

    /**
     * http://localhost:8000/kafka/test2
     *
     */
    @RequestMapping("/test2")
    public String test2() {
        template.send("topic-2-foo", "key-2", "hello listener 2!"); // Topic要在KafkaConfiguration里创建
        return "sent";
    }

    @KafkaListener(
            id = "id-2",
//            topics = {"topic-2-foo", "topic-2-bar"}, // 匹配多个Topic
            topicPattern = "topic-2-.*", // Topic正则匹配
            concurrency = "${kafka.topic2.concurrency:5}" // 消费端线程数, 注意Kafka不支持一个Partition被多线程并发消费, 分区数必须大于等于消费者线程数
    )
    public void onTopic2Received(@Payload String message,
                                 @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                 @Header(KafkaHeaders.RECEIVED_KEY) String key,
                                 @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                                 @Header(KafkaHeaders.OFFSET) long offSet) {
        logger.info("KafkaController received message from kafka (topic-2): " + message);
        logger.info("Message headers: topic=" + topic + ", key=" + key + ", partition=" + partition + ", offset=" + offSet);
        // TODO 注意: 如果这里抛出异常, 会触发Kafka消息重新消费(相同的消息重新在这里被消费, 如果一直抛出异常, 达到一定次数后会停止, 变成死信)
    }

}
