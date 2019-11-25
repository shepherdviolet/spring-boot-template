package template.demo.micrometer.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Micrometer示例
 */
@Configuration
@ComponentScan({
        "template.demo.micrometer.controller",
})
public class MicrometerConfiguration {

}
