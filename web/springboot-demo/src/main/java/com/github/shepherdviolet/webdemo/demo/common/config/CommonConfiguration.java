package com.github.shepherdviolet.webdemo.demo.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * SpringBoot通用示例
 */
@Configuration
@ComponentScan({
        "com.github.shepherdviolet.webdemo.demo.common.controller",
        "com.github.shepherdviolet.webdemo.demo.common.service",
})
public class CommonConfiguration {

}
