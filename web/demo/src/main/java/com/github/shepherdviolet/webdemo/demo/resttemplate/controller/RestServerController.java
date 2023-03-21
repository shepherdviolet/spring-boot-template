package com.github.shepherdviolet.webdemo.demo.resttemplate.controller;

import com.github.shepherdviolet.webdemo.demo.resttemplate.entity.SimpleRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * 作为RestTemplate的服务端
 */
@RestController
@RequestMapping("/resttemplate/server")
public class RestServerController {

    private static final Logger logger = LoggerFactory.getLogger(RestServerController.class);

    @RequestMapping("/get")
    public String get(@RequestParam String name, @RequestParam BigDecimal amount) {
        return "{\"name\":\"" + name + "\", \"amount\":" + amount + ", \"aliases\":[\"t1\",\"t2\",\"t3\"]}";
    }

    @RequestMapping("/post")
    public String post(@RequestBody SimpleRequest simpleRequest) {
        return "{\"name\":\"" + simpleRequest.getName() + "\", \"amount\":" + simpleRequest.getAmount() + ", \"aliases\":[\"t4\",\"t5\",\"t6\"]}";
    }

}
