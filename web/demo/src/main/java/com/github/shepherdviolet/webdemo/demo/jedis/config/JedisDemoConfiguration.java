//package com.github.shepherdviolet.webdemo.demo.jedis.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPoolConfig;
//import redis.clients.jedis.JedisSentinelPool;
//import redis.clients.util.Pool;
//
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * <p>Redis使用示例(适用于老系统, JDK7时, 用Jedis 2.9.0 + Redis 3.2.13)</p>
// *
// * 暂时只实现Sentinel.
// */
//@Configuration
//@ConditionalOnProperty(name = "jedis.enabled", havingValue = "true")
//@ComponentScan({
//        "com.github.shepherdviolet.webdemo.demo.jedis.controller",
//})
//public class JedisDemoConfiguration {
//
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//
//    /**
//     * Sentinel:
//     * host1:26379,host2:26379,host3:26379
//     */
//    @Value("${jedis.uri}")
//    private String uri;
//
//    @Value("${jedis.password:}")
//    private String password;
//
//    private String masterName = "mymaster"; // Sentinel的master集群名称
//    private int timeout = 4000; // 连接超时 或 读写超时, ms
//
//    @Bean("jedisPool")
//    public Pool<Jedis> jedisPool() {
//        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxTotal(8); // 可用连接实例的最大数目，默认为8, -1不限制
//        config.setMaxIdle(8); // 控制一个pool最多有多少个状态为idle(空闲)的jedis实例，默认值是8
//        config.setMaxWaitMillis(8000L); // 等待可用连接的最大时间，单位是毫秒，默认值为-1，表示永不超时。
//        config.setTestOnBorrow(true); // 在borrow(用)一个jedis实例时，是否提前进行validate(验证)操作；
//        config.setTestWhileIdle(true); // 在空闲时检查有效性, 默认false
//        config.setTestOnReturn(true); // 归还时是否进行有效性检查
//
//        return new JedisSentinelPool(masterName, parseUri(uri), config, timeout, password);
//    }
//
//    /**
//     * 解析URI
//     */
//    private Set<String> parseUri(String uri){
//        if (uri == null || (uri = uri.trim()).length() == 0) {
//            throw new RuntimeException("Jedis | property 'jedis.uri' is required");
//        }
//        logger.info("Jedis | URIs: " + uri);
//
//        // stand-alone or sentinel
//        String[] array = uri.split(",");
//        Set<String> uriSet = new HashSet<>(array.length * 2);
//        for (String u : array) {
//            u = u.trim();
//            if (u.length() > 0) {
//                uriSet.add(u);
//                logger.info("Jedis | URI: " + u);
//            }
//        }
//        if (uriSet.size() == 0) {
//            throw new RuntimeException("Jedis | property 'jedis.uri' is invalid, no valid uri contains");
//        }
//        return uriSet;
//    }
//
//}
