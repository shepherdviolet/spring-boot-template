package com.github.shepherdviolet.webdemo.demo.xmlconfig.controller;

import com.github.shepherdviolet.webdemo.demo.xmlconfig.entity.XmlBeanWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * XML方式配置示例
 *
 * @author S.Violet
 */
@RestController
@RequestMapping("/xmlconfig")
public class XmlConfigController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private XmlBeanWrapper xmlBeanWrapper;

    /**
     * http://localhost:8080/xmlconfig
     */
    @RequestMapping("")
    public String test() {
        return String.valueOf(xmlBeanWrapper);
    }

}
