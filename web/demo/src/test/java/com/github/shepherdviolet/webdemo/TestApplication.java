package com.github.shepherdviolet.webdemo;

import com.alicp.jetcache.autoconfigure.JetCacheAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 测试入口
 *
 * @author Sviolet
 */
@SpringBootApplication(
        //这个方法只能排除spring.factories里声明的自动配置类, 对@Import导入或者@Enable注解启用的无效!
        exclude = {
                JetCacheAutoConfiguration.class, // 排除JetCache自动配置, 因为依赖了JetCache却不设置它, 启动会报错(而且我们自定义了GlobalCacheConfig也用不上它)
//                DataSourceAutoConfiguration.class, // 排除数据库配置(可选)
        }
)
//扫包路径
@ComponentScan({
        "com.github.shepherdviolet.webdemo.demo.mockito.config",
})
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
