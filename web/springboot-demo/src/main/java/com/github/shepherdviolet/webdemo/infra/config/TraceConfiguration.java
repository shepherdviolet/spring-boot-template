package com.github.shepherdviolet.webdemo.infra.config;

import com.github.shepherdviolet.glacimon.java.collections.LambdaBuildable;
import com.github.shepherdviolet.webdemo.infra.trace.TraceHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 日志追踪
 */
@Configuration
//扫包路径. 如果不指定包路径(@ComponentScan()), Spring会扫描当前类所在包路径下所有的组件(包括子包)
@ComponentScan({
        "com.github.shepherdviolet.webdemo.infra.trace",
})
public class TraceConfiguration implements LambdaBuildable {

    /**
     * 添加MVC拦截器
     */
    @Bean
    public WebMvcConfigurer webMvcConfigurer(TraceHandlerInterceptor traceHandlerInterceptor){
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(traceHandlerInterceptor);
            }
        };
    }

}
