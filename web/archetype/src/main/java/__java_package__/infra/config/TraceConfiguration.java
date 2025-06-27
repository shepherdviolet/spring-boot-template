// <replace-by> package ${java_package}.infra.config;
package __java_package__.infra.config;

// <replace-by> import ${java_package}.infra.trace.TraceHandlerInterceptor;
import __java_package__.infra.trace.TraceHandlerInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 日志追踪
 */
@Configuration
@ComponentScan({
        // <replace-by>         "${java_package}.infra.trace",
        "__java_package__.infra.trace",
})
public class TraceConfiguration {

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
