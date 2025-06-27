// <replace-by> package ${java_package}.core.config;
package __java_package__.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 */
@Configuration
@ComponentScan({
        // <replace-by>         "${java_package}.core.controller",
        "__java_package__.core.controller",
})
public class CoreConfiguration {

}
