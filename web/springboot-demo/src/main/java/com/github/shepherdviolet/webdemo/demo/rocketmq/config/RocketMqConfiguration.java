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

    @Value("${glacispring.helper.rocketmq.namesrv}")
    private String nameServer;

    /**
     * 生产者(Standard和Helper方式用, 如果用Starter方式, 请把这个注掉!)
     */
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
