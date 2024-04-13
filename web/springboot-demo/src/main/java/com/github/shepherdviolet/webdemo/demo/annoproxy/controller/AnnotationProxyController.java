package com.github.shepherdviolet.webdemo.demo.annoproxy.controller;

import com.github.shepherdviolet.webdemo.demo.annoproxy.customservice.CustomService;
import com.github.shepherdviolet.webdemo.demo.annoproxy.rpcservice.ClassServices;
import com.github.shepherdviolet.webdemo.demo.annoproxy.rpcservice.InterfaceServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 方法注解生成代理类的示例
 * @author S.Violet
 */
@RestController
@RequestMapping("/annoproxy")
public class AnnotationProxyController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ClassServices classServices;

    @Autowired
    private InterfaceServices interfaceServices;

    @Autowired
    private CustomService customService;

    /**
     * http://localhost:8000/annoproxy/service1
     */
    @RequestMapping("/service1")
    public String service1(){
        logger.info("service1");
        return classServices.service1("request body 1");
    }

    /**
     * http://localhost:8000/annoproxy/service2
     */
    @RequestMapping("/service2")
    public String service2(){
        logger.info("service2");
        return interfaceServices.service2("request body 2");
    }

    /**
     * http://localhost:8000/annoproxy/service3
     */
    @RequestMapping("/service3")
    public String service3(){
        logger.info("service3");
        return customService.handle("i am input");
    }

}
