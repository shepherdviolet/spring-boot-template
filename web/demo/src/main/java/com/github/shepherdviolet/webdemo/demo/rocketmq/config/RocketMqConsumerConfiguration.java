package com.github.shepherdviolet.webdemo.demo.rocketmq.config;

import com.github.shepherdviolet.glacimon.spring.helper.rocketmq.consumer.EnableRocketMqHelper;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
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
        "com.github.shepherdviolet.webdemo.demo.rocketmq.consumer",
})
@ConditionalOnExpression("${template.demo.rocketmq.consumer:true}")
@EnableRocketMqHelper
public class RocketMqConsumerConfiguration {

    @Value("${glacispring.helper.rocketmq.namesrv}")
    private String nameServer;

    @Bean(name = "customConsumer", destroyMethod = "shutdown")
    public DefaultMQPushConsumer customConsumer() throws MQClientException {
        //defaultConsumerGroup消费组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("glacispringCustomConsumerTest1");
        //NameServer地址, IP:PORT;IP:PORT
        consumer.setNamesrvAddr(nameServer);
        //订阅Topic为simple, 任意TAG的消息
        consumer.subscribe("glacispringCustomConsumerTest", "*");
        //从队列头部取消息
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //注意!!! consumer不要启动, 也不要绑定listener, 因为这些操作会在绑定方法时自动进行
        return consumer;
    }

}
