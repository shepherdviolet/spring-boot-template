package com.github.shepherdviolet.webdemo.basic.config;

import com.github.shepherdviolet.glacimon.spring.x.monitor.txtimer.def.EnableTxTimerSpringConfig;
import com.github.shepherdviolet.webdemo.basic.interceptor.BasicHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 基本模板配置类
 */
@Configuration
@EnableTxTimerSpringConfig
//扫包路径. 如果不指定包路径(@ComponentScan()), Spring会扫描当前类所在包路径下所有的组件(包括子包)
@ComponentScan({
        "com.github.shepherdviolet.webdemo.basic.controller",
        "com.github.shepherdviolet.webdemo.basic.interceptor",
        "com.github.shepherdviolet.webdemo.basic.error",
})
public class BasicConfiguration {

    @Bean
    public WebMvcConfigurer webMvcConfigurer(BasicHandlerInterceptor basicHandlerInterceptor){
        return new WebMvcConfigurer() {
            /**
             * 添加MVC拦截器
             */
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(basicHandlerInterceptor);
            }
        };
    }

}
