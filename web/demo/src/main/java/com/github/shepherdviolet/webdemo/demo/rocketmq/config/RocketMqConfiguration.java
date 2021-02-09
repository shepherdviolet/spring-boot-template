package com.github.shepherdviolet.webdemo.demo.rocketmq.config;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * RocketMQ示例
 *
 * @author S.Violet
 */
@Configuration
@ComponentScan({
        "com.github.shepherdviolet.webdemo.demo.rocketmq.controller",
        "com.github.shepherdviolet.webdemo.demo.rocketmq.producer",
})
public class RocketMqConfiguration {

    @Value("${slate.common.rocketmq.namesrv:localhost:9876}")
    private String nameServer;

    @Bean(name = "defaultProducer", destroyMethod = "shutdown")
    public DefaultMQProducer defaultProducer() throws MQClientException {
        //defaultProducerGroup为生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("default-producer");
        //NameServer地址, IP:PORT;IP:PORT
        producer.setNamesrvAddr(nameServer);
        //可以设置线程池
//        producer.setAsyncSenderExecutor();
//        producer.setCallbackExecutor();
        //启动
        producer.start();
        return producer;
    }

}
