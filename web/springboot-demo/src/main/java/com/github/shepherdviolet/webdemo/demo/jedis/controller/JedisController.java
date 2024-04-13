//package com.github.shepherdviolet.webdemo.demo.jedis.controller;
//
//import com.github.shepherdviolet.glacimon.java.misc.DateTimeUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import redis.clients.jedis.Jedis;
//import redis.clients.util.Pool;
//
///**
// * Redis使用示例(适用于老系统, JDK7时, 用Jedis 2.9.0 + Redis 3.2.13)
// *
// * @author S.Violet
// */
//@RestController
//@RequestMapping("/jedis")
//public class JedisController {
//
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Autowired
//    private Pool<Jedis> jedisPool;
//
//    /**
//     * http://localhost:8000/jedis/test
//     */
//    @RequestMapping("/test")
//    public String lettuce() {
//        try (Jedis jedis = jedisPool.getResource()) {
//            String ret = jedis.get("springboot-demo-jedis-1");
//            jedis.set("springboot-demo-jedis-1", DateTimeUtils.currentDateTimeString());
//            return ret;
//        }
//    }
//
//}
