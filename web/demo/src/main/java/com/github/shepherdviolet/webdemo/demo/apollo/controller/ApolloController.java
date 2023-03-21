package com.github.shepherdviolet.webdemo.demo.apollo.controller;

import com.github.shepherdviolet.glacimon.spring.helper.apollo.ApolloRefreshableProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Apollo配置中心的示例
 * @author S.Violet
 */
@RestController
@RequestMapping("/apollo")
public class ApolloController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("demoRefreshableProperties")
    private ApolloRefreshableProperties demoRefreshableProperties;

    /**
     * http://localhost:8000/apollo/refreshable?key=key1
     *
     * 1.在Apollo上新建一个应用, app.id为: spring-boot-demo
     * 2.然后在application配置里, 添加前缀为"prefix1."的参数, 例如: prefix1.aaa
     * 3.请求: http://localhost:8000/apollo/refreshable?key=aaa
     */
    @RequestMapping("/refreshable")
    public String service1(@RequestParam String key){
        logger.info("refreshable " + key);
        return demoRefreshableProperties.get(key);
    }

}
