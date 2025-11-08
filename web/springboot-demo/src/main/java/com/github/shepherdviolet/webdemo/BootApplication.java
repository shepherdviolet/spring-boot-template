package com.github.shepherdviolet.webdemo;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * 入口
 *
 * @author Sviolet
 */
@SpringBootApplication(
        //这个方法只能排除spring.factories里声明的自动配置类, 对@Import导入或者@Enable注解启用的无效!
        exclude = {
                SecurityAutoConfiguration.class, // 排除(禁用)SpringSecurity (未依赖spring-boot-starter-security则无需禁用)
                ManagementWebSecurityAutoConfiguration.class, // 排除(禁用)SpringSecurity (未依赖spring-boot-starter-security则无需禁用)
//                DataSourceAutoConfiguration.class, // 排除数据库自动配置 (如果不需要连接数据库的话)
        },
        //扫包路径, 等同于XML中的<context:component-scan base-package="com.aaa.**.xxx;com.bbb.**.xxx"/>
        //P.S. @SpringBootApplication 约等于 @EnableAutoConfiguration + @ComponentScan
        //P.S. 如果不设置scanBasePackages, Spring会扫描当前类(启动类)所在包路径下所有的组件(包括子包)
        scanBasePackages = {
                "com.github.shepherdviolet.webdemo.infra.config",
                "com.github.shepherdviolet.webdemo.core.config",
                "com.github.shepherdviolet.webdemo.demo.common.config",
                "com.github.shepherdviolet.webdemo.demo.validate.config",
                "com.github.shepherdviolet.webdemo.demo.wechatpush.config",
                "com.github.shepherdviolet.webdemo.demo.annoproxy.config",
                "com.github.shepherdviolet.webdemo.demo.aspectj.config",
                "com.github.shepherdviolet.webdemo.demo.test.config",
                "com.github.shepherdviolet.webdemo.demo.mybatis.config",
                "com.github.shepherdviolet.webdemo.demo.micrometer.config",
                "com.github.shepherdviolet.webdemo.demo.redis.config",
                "com.github.shepherdviolet.webdemo.demo.shorturl.config",
                "com.github.shepherdviolet.webdemo.demo.resttemplate.config",
                "com.github.shepherdviolet.webdemo.demo.actuator.config",
                "com.github.shepherdviolet.webdemo.demo.qwen2vl.config",
//                "com.github.shepherdviolet.webdemo.demo.security.config",
//                "com.github.shepherdviolet.webdemo.demo.sentinel.config",
//                "com.github.shepherdviolet.webdemo.demo.apollo.config",
//                "com.github.shepherdviolet.webdemo.demo.rocketmq.config",
//                "com.github.shepherdviolet.webdemo.demo.kafka.config",
        }
)
//Spring Boot Admin server (控制台服务端, http://localhost:8000/admin, 容器需为Tomcat, 改过URL(默认没/admin), 见springboot-admin.yaml), 示例为client-server直连方式, 如需通过Eureka发现请自行谷歌
@EnableAdminServer
public class BootApplication {

//    private static volatile boolean shutdown = false;
//    private static final Logger logger = LoggerFactory.getLogger(BootApplication.class);


    public static void main(String[] args) {

        //Sentinel启动参数
        sentinelSettings();

        //WEB项目启动
        SpringApplication.run(BootApplication.class, args);

        //非WEB项目启动
        //Spring会根据Classpath下是否存在Servlet等类来判断是否WEB模式, 判断为WEB模式时, 如果未依赖spring-boot-starter-web, 会直接结束进程(不会进入后面的自旋循环)
        //所以强制设置为非WEB模式, 然后再用自旋循环可以解决启动后立刻结束的问题
//        Thread thread = Thread.currentThread();
//        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
//            @Override
//            public void run() {
//                shutdown = true;
//                thread.interrupt();
//            }
//        }));
//        try {
//            new SpringApplicationBuilder(ProviderBoot.class)
//                    .web(WebApplicationType.NONE)
//                    .run(args);
//        } catch (Throwable t) {
//            logger.error("Error while application starting", t);
//            return;
//        }
//        while (!shutdown) {
//            try {
//                Thread.sleep(60000L);
//            } catch (InterruptedException ignored) {
//            }
//        }
    }

    private static void sentinelSettings(){
        //将RT最大值从4.9秒改成120秒
        System.setProperty("csp.sentinel.statistic.max.rt", "120000");
    }

}
