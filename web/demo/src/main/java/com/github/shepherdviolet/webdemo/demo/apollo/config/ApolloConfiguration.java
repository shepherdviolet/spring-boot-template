package com.github.shepherdviolet.webdemo.demo.apollo.config;

import com.github.shepherdviolet.glacimon.spring.helper.apollo.ApolloRefreshableProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Apollo配置中心示例, 必须添加启动参数-Denv=dev/fat/uat/pro
 *
 * @author S.Violet
 */
@Configuration
@ComponentScan({
        "com.github.shepherdviolet.webdemo.demo.apollo.controller"
})
public class ApolloConfiguration {

    @Bean
    public ApolloRefreshableProperties demoRefreshableProperties(){
        return new ApolloRefreshableProperties()
                .setNamespace("application")
                .setPrefix("prefix1.");
    }

}
