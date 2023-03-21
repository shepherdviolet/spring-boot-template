package com.github.shepherdviolet.webdemo.demo.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Kafka示例
 */
@Configuration
@ComponentScan({
        "com.github.shepherdviolet.webdemo.demo.kafka.controller",
})
public class KafkaConfiguration {

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("topic-1")
                .partitions(10)
                .replicas(2)
                .build();
    }

    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name("topic-2-foo")
                .partitions(10)
                .replicas(2)
                .build();
    }

}
