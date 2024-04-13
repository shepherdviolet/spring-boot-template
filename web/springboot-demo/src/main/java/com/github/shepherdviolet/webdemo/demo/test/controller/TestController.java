package com.github.shepherdviolet.webdemo.demo.test.controller;

import com.github.shepherdviolet.webdemo.demo.test.service.InnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Springboot test 示例
 */
@RestController
@RequestMapping("/test")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private InnerService innerService;

    /**
     * http://localhost:8000/test/greet
     */
    @RequestMapping("/greet")
    public String greet() {
        return "hello spring boot test";
    }

    /**
     * http://localhost:8000/test/service
     */
    @RequestMapping("/service")
    public String service(@RequestParam String id) {
        return innerService.getData(id);
    }

}
