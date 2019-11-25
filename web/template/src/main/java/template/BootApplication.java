package template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

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
        "template.basic.config",
        "template.demo.common.config",
        "template.demo.validate.config",
        "template.demo.fileupload.config",
        "template.demo.properties.config",
        "template.demo.xmlconfig.config",
        "template.demo.wechatpush.config",
        "template.demo.annoproxy.config",
        "template.demo.aspectj.config",
        "template.demo.mockito.config",
        "template.demo.mybatis.config",
        "template.demo.micrometer.config",
        "template.demo.schedule.config",
        "template.demo.sentinel.config",
//        "template.demo.apollo.config",
//        "template.demo.rocketmq.config",
})
//Spring Boot Admin server (控制台服务端, 容器需改为Tomcat, 控制台地址: http://localhost:8080)
//@EnableAdminServer
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
    public WebServerFactoryCustomizer<ConfigurableTomcatWebServerFactory> webServerFactoryCustomizer() {
        return factory -> {
            factory.addConnectorCustomizers(connector -> {
                connector.setAttribute("acceptorThreadCount", "2");
                connector.setAttribute("connectionTimeout", "30000");
                connector.setAttribute("asyncTimeout", "30000");
                connector.setAttribute("enableLookups", "false");
                connector.setAttribute("compression", "on");
                connector.setAttribute("compressionMinSize", "2048");
                connector.setAttribute("redirectPort", "8443");
            });
        };
    }

    /**
     * Undertow调优
     */
//    @Bean
//    public WebServerFactoryCustomizer<ConfigurableUndertowWebServerFactory> webServerFactoryCustomizer() {
//        return factory -> {
//            factory.addDeploymentInfoCustomizers(deploymentInfo -> {
//                //禁用HTTP TRACE, 测试:curl -v -X TRACE http://localhost:8080/
//                deploymentInfo.addSecurityConstraint(new SecurityConstraint()
//                        .addWebResourceCollection(
//                                new WebResourceCollection()
//                                        .addUrlPattern("/*")
//                                        .addHttpMethod(HttpMethod.TRACE.toString())
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
