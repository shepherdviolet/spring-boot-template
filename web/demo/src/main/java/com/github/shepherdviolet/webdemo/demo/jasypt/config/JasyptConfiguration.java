package com.github.shepherdviolet.webdemo.demo.jasypt.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * jasypt-spring-boot-starter示例: 参数加密
 *
 * springboot/cloud上不需要Enable, 引入依赖, 配置密码即可; spring项目需要Enable或者配置XML
 * github: https://github.com/ulisesbocchio/jasypt-spring-boot
 */
@Configuration
@ComponentScan({
        "com.github.shepherdviolet.webdemo.demo.jasypt.controller"
})
public class JasyptConfiguration {

}
