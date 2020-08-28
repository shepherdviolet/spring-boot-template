package com.github.shepherdviolet.webdemo;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import sviolet.slate.common.util.common.LambdaBuildable;

/**
 * 入口
 *
 * @author Sviolet
 */
@SpringBootApplication(
        //这个方法只能排除spring.factories里声明的自动配置类, 对@Import导入或者@Enable注解启用的无效!
//        exclude = {
//                DataSourceAutoConfiguration.class//排除数据库配置(可选)
//        }
)
//扫包路径, 等同于XML中的<context:component-scan base-package="com.aaa.**.xxx;com.bbb.**.xxx"/>
@ComponentScan({
        "com.github.shepherdviolet.webdemo.basic.config",
        "com.github.shepherdviolet.webdemo.demo.common.config",
        "com.github.shepherdviolet.webdemo.demo.validate.config",
        "com.github.shepherdviolet.webdemo.demo.fileupload.config",
        "com.github.shepherdviolet.webdemo.demo.properties.config",
        "com.github.shepherdviolet.webdemo.demo.xmlconfig.config",
        "com.github.shepherdviolet.webdemo.demo.wechatpush.config",
        "com.github.shepherdviolet.webdemo.demo.annoproxy.config",
        "com.github.shepherdviolet.webdemo.demo.aspectj.config",
        "com.github.shepherdviolet.webdemo.demo.mockito.config",
        "com.github.shepherdviolet.webdemo.demo.mybatis.config",
        "com.github.shepherdviolet.webdemo.demo.micrometer.config",
        "com.github.shepherdviolet.webdemo.demo.schedule.config",
//        "com.github.shepherdviolet.webdemo.demo.sentinel.config",
//        "com.github.shepherdviolet.webdemo.demo.apollo.config",
//        "com.github.shepherdviolet.webdemo.demo.rocketmq.config",
})
//Spring Boot Admin server (控制台服务端, 容器需改为Tomcat, 控制台地址: http://localhost:8000/admin, 改过URL(默认没/admin), 见application.yaml)
@EnableAdminServer
public class BootApplication implements LambdaBuildable {

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

//    /**
//     * 配置Slate监听器(可选)
//     * 依赖了com.github.shepherdviolet:slate-springboot后, 无需手动配置
//     */
//    @Bean
//    public ServletContextListener slateServletContextListener() {
//        return new SlateServletContextListener();
//    }

    /**
     * Tomcat调优
     */
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> {
            //[安全]禁用不安全的HTTP方法, 测试:curl -v -X TRACE http://localhost:8000/
            factory.addContextCustomizers(context -> {
                context.addConstraint(buildObject(() -> {
                    SecurityConstraint securityConstraint = new SecurityConstraint();
                    securityConstraint.setUserConstraint("CONFIDENTIAL");
                    securityConstraint.addCollection(buildObject(() -> {
                        SecurityCollection securities = new SecurityCollection();
                        securities.addPattern("/*");
                        securities.addMethod("TRACE");
                        securities.addMethod("HEAD");
                        securities.addMethod("DELETE");
                        securities.addMethod("SEARCH");
                        securities.addMethod("PROPFIND");
                        securities.addMethod("COPY");
                        securities.addMethod("OPTIONS");
                        securities.addMethod("PUT");
                        return securities;
                    }));
                    return securityConstraint;
                }));
            });
            //调整连接参数
            factory.addConnectorCustomizers(connector -> {
                connector.setProperty("acceptorThreadCount", "2");
                connector.setProperty("connectionTimeout", "30000");
                connector.setProperty("asyncTimeout", "30000");
                connector.setProperty("enableLookups", "false");
                connector.setProperty("compression", "on");
                connector.setProperty("compressionMinSize", "2048");
                connector.setProperty("redirectPort", "8443");
            });
        };
    }

    /**
     * Undertow调优
     */
//    @Bean
//    public WebServerFactoryCustomizer<UndertowServletWebServerFactory> webServerFactoryCustomizer() {
//        return factory -> {
//            factory.addDeploymentInfoCustomizers(deploymentInfo -> {
//                //[安全]禁用不安全的HTTP方法, 测试:curl -v -X TRACE http://localhost:8000/
//                deploymentInfo.addSecurityConstraint(new SecurityConstraint()
//                        .addWebResourceCollection(
//                                new WebResourceCollection()
//                                        .addUrlPattern("/*")
//                                        .addHttpMethod("TRACE")
//                                        .addHttpMethod("HEAD")
//                                        .addHttpMethod("DELETE")
//                                        .addHttpMethod("SEARCH")
//                                        .addHttpMethod("PROPFIND")
//                                        .addHttpMethod("COPY")
//                                        .addHttpMethod("OPTIONS")
//                                        .addHttpMethod("PUT")
//                        )
//                );
//            });
//        };
//    }

    private static void sentinelSettings(){
        //将RT最大值从4.9秒改成120秒
        System.setProperty("csp.sentinel.statistic.max.rt", "120000");
    }

}
