package com.github.shepherdviolet.webdemo.demo.aspectj.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.shepherdviolet.webdemo.demo.aspectj.service.Service1;
import com.github.shepherdviolet.webdemo.demo.aspectj.service.Service2;
import com.github.shepherdviolet.webdemo.demo.aspectj.service.Service3;
import com.github.shepherdviolet.webdemo.demo.aspectj.service.TestAJService;

/**
 * AspectJ示例
 */
@RestController
@RequestMapping("/aspectj")
public class AspectJController {

    private static final Logger logger = LoggerFactory.getLogger(AspectJController.class);

    @Autowired
    private Service1 service1;

    @Autowired
    private Service2 service2;

    @Autowired
    private Service3 service3;

    @Autowired
    private TestAJService testAJService;

    /**
     * http://localhost:8000/aspectj
     */
    @RequestMapping("")
    public String test() {
        logger.info("------------service1 method1------------");
        service1.method1(11);
        logger.info("------------service1 method2------------");
        service1.method2(12);
        logger.info("------------service1 method3------------");
        service1.method3(13, 13f);
        logger.info("------------service1 method4------------");
        try { service1.method4("14"); } catch (Throwable ignore) { }
        logger.info("------------service1 method5------------");
        service1.method5("15");
        logger.info("------------service2 method1------------");
        service2.method1("21");
        logger.info("------------service3 method1------------");
        service3.method1("31");
        logger.info("------------testAJService method1------------");
        testAJService.method1("aj1");
        return "ok";
    }

}
