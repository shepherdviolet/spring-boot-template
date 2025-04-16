package com.github.shepherdviolet.webdemo.demo.redis.config;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.github.shepherdviolet.webdemo.demo.redis.jetcache.JetCacheConfiguration;
import com.github.shepherdviolet.webdemo.demo.redis.lettuce.LettuceConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <p>Redis使用示例(JetCache+Lettuce)</p>
 *
 * <p>使用帮助:</p>
 *
 * <p>1.使用JetCache进行缓存访问, 适用于简单的缓存场景, 请参考JetCache官方文档</p>
 * <p>2.使用lettuce进行Redis访问, 适用于复杂场景, 注入'RedisCommands'访问lettuce API, 请参考lettuce官方文档</p>
 */
@Configuration
@ConditionalOnProperty(name = "redis.enabled", havingValue = "true")
@Import({
        LettuceConfiguration.class,
        JetCacheConfiguration.class,
})
@ComponentScan({
        "com.github.shepherdviolet.webdemo.demo.redis.cache",
        "com.github.shepherdviolet.webdemo.demo.redis.controller",
})
@EnableMethodCache(basePackages = {
        "com.github.shepherdviolet.webdemo.demo.redis.cache",
})
public class RedisDemoConfiguration {

}
