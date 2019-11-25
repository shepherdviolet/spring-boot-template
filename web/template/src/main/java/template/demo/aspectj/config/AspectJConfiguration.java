package template.demo.aspectj.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * AspectJ示例
 */
@Configuration
@ComponentScan({
        "template.demo.aspectj.controller",
        "template.demo.aspectj.service",
        "template.demo.aspectj.aspect"
})
//启用AspectJ并用CGLIB做代理
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AspectJConfiguration {

}
