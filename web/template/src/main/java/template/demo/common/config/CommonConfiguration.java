package template.demo.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * SpringBoot通用示例
 */
@Configuration
@ComponentScan({
        "template.demo.common.controller",
        "template.demo.common.service",
        "template.demo.common.logic",
})
public class CommonConfiguration {

}
