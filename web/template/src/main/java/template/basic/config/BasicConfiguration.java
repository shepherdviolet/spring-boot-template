package template.basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sviolet.slate.common.x.monitor.txtimer.def.EnableTxTimerSpringConfig;
import template.basic.interceptor.BasicHandlerInterceptor;

/**
 * 基本模板配置类
 */
@Configuration
@EnableTxTimerSpringConfig
@ComponentScan({
        "template.basic.controller",
        "template.basic.interceptor",
        "template.basic.error",
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
