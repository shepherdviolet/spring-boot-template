// <replace-by> package ${java_package}.basic.config;
package __java_package__.basic.config;

// <replace-by> import ${java_package}.basic.interceptor.BasicHandlerInterceptor;
import __java_package__.basic.interceptor.BasicHandlerInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类
 */
@Configuration
@ComponentScan({
        // <replace-by>         "${java_package}.basic.controller",
        "__java_package__.basic.controller",
        // <replace-by>         "${java_package}.basic.interceptor",
        "__java_package__.basic.interceptor",
})
public class BasicConfiguration {

    @Bean
    public WebMvcConfigurer webMvcConfigurer(BasicHandlerInterceptor basicHandlerInterceptor){
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(basicHandlerInterceptor);
            }
        };
    }

}
