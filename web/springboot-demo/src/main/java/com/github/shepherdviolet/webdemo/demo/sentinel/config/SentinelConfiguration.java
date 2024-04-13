package com.github.shepherdviolet.webdemo.demo.sentinel.config;

import com.github.shepherdviolet.glacimon.spring.helper.sentinel.EnableEzSentinel;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 熔断限流Sentinel示例
 *
 * @author S.Violet
 */
@Configuration
@EnableScheduling
@ComponentScan({
        "com.github.shepherdviolet.webdemo.demo.sentinel.controller"
})
//启用EzSentinel
@EnableEzSentinel
public class SentinelConfiguration {

}
