package com.github.shepherdviolet.webdemo.demo.redis.controller;

import com.github.shepherdviolet.glacimon.java.misc.DateTimeUtils;
import com.github.shepherdviolet.webdemo.demo.redis.lettuce.RedisCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Redis使用示例(Lettuce)
 *
 * @author S.Violet
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisCommands redisCommands;

    /**
     * http://localhost:8000/redis/lettuce
     */
    @RequestMapping("/lettuce")
    public String lettuce() {
        String ret = redisCommands.stringCommands().get("springboot-demo-lettuce-1");
        redisCommands.stringCommands().set("springboot-demo-lettuce-1", DateTimeUtils.currentDateTimeString());
        return ret;
    }

}
