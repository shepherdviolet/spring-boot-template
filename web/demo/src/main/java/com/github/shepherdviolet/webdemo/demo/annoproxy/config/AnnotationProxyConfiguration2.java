package com.github.shepherdviolet.webdemo.demo.annoproxy.config;

import com.github.shepherdviolet.webdemo.demo.annoproxy.custom.EnableCustomProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 接口类实例化(有实现)示例
 */
@Configuration
@ComponentScan({
        "com.github.shepherdviolet.webdemo.demo.annoproxy.customservice"
})
//使用自定义开关注解声明:实例化com.github.shepherdviolet.webdemo.demo.annoproxy.customservice路径下的接口
@EnableCustomProxy(
        basePackages = {
                "com.github.shepherdviolet.webdemo.demo.annoproxy.customservice"
        }
)
public class AnnotationProxyConfiguration2 {

}
