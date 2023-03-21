package com.github.shepherdviolet.webdemo.demo.annoproxy.config;

import com.github.shepherdviolet.glacimon.spring.x.config.interfaceinst.EnableInterfaceInstantiation;
import com.github.shepherdviolet.webdemo.demo.annoproxy.fakerpc.EnableFakeRpc;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 方法注解切面(非AspectJ)示例 + 接口类实例化(空实现)示例
 */
@Configuration
@ComponentScan({
        "com.github.shepherdviolet.webdemo.demo.annoproxy.controller",
        "com.github.shepherdviolet.webdemo.demo.annoproxy.service",
        "com.github.shepherdviolet.webdemo.demo.annoproxy.rpcservice"
})
//启用FakeRpc并使用CGLIB代理(Springboot2以上默认开启CGLIB, 即使这里是false)
@EnableFakeRpc(basePackages = "com.github.shepherdviolet.webdemo.demo.annoproxy.rpcservice", proxyTargetClass = true)
//实例化com.github.shepherdviolet.webdemo.demo.annoproxy.rpcservice路径下的接口
@EnableInterfaceInstantiation(basePackages = {"com.github.shepherdviolet.webdemo.demo.annoproxy.rpcservice"})
public class AnnotationProxyConfiguration {

}
