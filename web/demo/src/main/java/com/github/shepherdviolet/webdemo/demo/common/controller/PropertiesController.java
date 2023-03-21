package com.github.shepherdviolet.webdemo.demo.common.controller;

import com.github.shepherdviolet.webdemo.demo.common.prop.BindProperties;
import com.github.shepherdviolet.webdemo.demo.common.prop.YamlProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

/**
 * 配置文件加载示例(yml/properties)
 *
 * @author S.Violet
 */
@RestController
@RequestMapping("/common/properties")
public class PropertiesController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${demo.properties.general.greet}")
    private String generalGreet;

    @Autowired
    private BindProperties bindProperties;

    @Autowired
    private YamlProperties yamlProperties;

    @Autowired
    @Qualifier("myProperties")
    private Properties myProperties;

    /**
     * http://localhost:8000/common/properties/
     */
    @RequestMapping("")
    public String test() {
        logger.info(generalGreet + "<br>" + bindProperties + "<br>" + yamlProperties + "<br>" + myProperties);
        return generalGreet + "<br>" + bindProperties + "<br>" + yamlProperties + "<br>" + myProperties;
    }

}
