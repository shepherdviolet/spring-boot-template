package com.github.shepherdviolet.webdemo.demo.aspectj.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * AspectJ示例
 */
@Configuration
@ComponentScan({
        "com.github.shepherdviolet.webdemo.demo.aspectj.controller",
        "com.github.shepherdviolet.webdemo.demo.aspectj.service",
        "com.github.shepherdviolet.webdemo.demo.aspectj.aspect"
})
//启用AspectJ并用CGLIB做代理(Springboot2以上默认开启CGLIB, 即使这里是false)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AspectJConfiguration {

}
