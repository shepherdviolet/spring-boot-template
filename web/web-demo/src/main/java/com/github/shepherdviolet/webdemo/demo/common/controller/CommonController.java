package com.github.shepherdviolet.webdemo.demo.common.controller;

import com.github.shepherdviolet.webdemo.demo.common.service.SampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SpringBoot通用示例
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private SampleService sampleService;

    /**
     * http://localhost:8080/common
     */
    @RequestMapping("")
    public String test() {
        return sampleService.handle("Raw Data");
    }

}
