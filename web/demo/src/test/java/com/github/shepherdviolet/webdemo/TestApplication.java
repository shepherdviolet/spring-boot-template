package com.github.shepherdviolet.webdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 测试上下文配置
 *
 * @author Sviolet
 */
@SpringBootApplication(
        //这个方法只能排除spring.factories里声明的自动配置类, 对@Import导入或者@Enable注解启用的无效!
        excludeName = {
                "com.alicp.jetcache.autoconfigure.JetCacheAutoConfiguration", // 排除JetCache自动配置 (依赖了JetCache却不设置它, 启动会报错, 我们自定义了GlobalCacheConfig用不上它)
                "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration", // 排除数据源自动配置(暂不测试它)
                "com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure", // 排除druid数据库连接池的自动配置(暂不测试它, 而且在工程依赖druid时构建test会报错)
                "org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration", // 排除(禁用)SpringSecurity (未依赖spring-boot-starter-security则无需排除)
                "org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration", // 排除(禁用)SpringSecurity (未依赖spring-boot-starter-security则无需排除)
        }
)
//扫包路径
@ComponentScan({
        "com.github.shepherdviolet.webdemo.demo.test.config",
})
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
