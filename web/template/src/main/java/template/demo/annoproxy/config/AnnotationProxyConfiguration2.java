package template.demo.annoproxy.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import template.demo.annoproxy.custom.EnableCustomProxy;

/**
 * 接口类实例化(有实现)示例
 */
@Configuration
@ComponentScan({
        "template.demo.annoproxy.customservice"
})
//使用自定义开关注解声明:实例化template.demo.annoproxy.customservice路径下的接口
@EnableCustomProxy(
        basePackages = {
                "template.demo.annoproxy.customservice"
        }
)
public class AnnotationProxyConfiguration2 {

}
