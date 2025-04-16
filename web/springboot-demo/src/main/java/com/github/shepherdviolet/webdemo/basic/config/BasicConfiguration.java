package com.github.shepherdviolet.webdemo.basic.config;

import com.github.shepherdviolet.glacimon.java.collections.LambdaBuildable;
import com.github.shepherdviolet.glacimon.spring.x.monitor.txtimer.def.EnableTxTimerSpringConfig;
import com.github.shepherdviolet.webdemo.basic.filter.EnhancedCharacterEncodingFilter;
import com.github.shepherdviolet.webdemo.basic.interceptor.BasicHandlerInterceptor;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.Encoding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 基本模板配置类
 */
@Configuration
@EnableTxTimerSpringConfig
//扫包路径. 如果不指定包路径(@ComponentScan()), Spring会扫描当前类所在包路径下所有的组件(包括子包)
@ComponentScan({
        "com.github.shepherdviolet.webdemo.basic.controller",
        "com.github.shepherdviolet.webdemo.basic.interceptor",
})
public class BasicConfiguration implements LambdaBuildable {

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
                        securities.addMethod("OPTIONS"); // CORS 跨域有用
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
     * Tomcat优化: 根据SpEL表达式配置的规则, 动态调整x-www-form-urlencoded表单请求的解码字符集.
     * 一般情况下, Content-Type=application/x-www-form-urlencoded是不指定charset的, Springboot默认用UTF-8解码FROM表单.
     * 如果请求方用其他字符编码, 可以用servlet.encoding.charset=GBK指定字符集.
     * 如果请求方用多种字符编码, 则需要自定义CharacterEncodingFilter, 实现动态字符集. (即此处的EnhancedCharacterEncodingFilter)
     *
     * 注意, 不同版本的Springboot配置CharacterEncodingFilter的源码有区别, 搜索HttpEncodingAutoConfiguration参考源码配置.
     *
     * ## set dynamic charset for x-www-form-urlencoded, SpEL root object is HttpServletRequest
     * server:
     *   encoding-filter:
     *     enable: true
     *     rules:
     *       GBK: "'application/x-www-form-urlencoded; charset=gbk'.equalsIgnoreCase(getContentType())"
     */

    @Bean
    @ConditionalOnProperty("server.encoding-filter.enable")
    @ConfigurationProperties("server.encoding-filter")
    public CharacterEncodingFilter enhancedCharacterEncodingFilter(ServerProperties properties) {
        Encoding encoding = properties.getServlet().getEncoding();
        EnhancedCharacterEncodingFilter filter = new EnhancedCharacterEncodingFilter();
        filter.setEncoding(encoding.getCharset().name());
        filter.setForceRequestEncoding(encoding.shouldForce(org.springframework.boot.web.servlet.server.Encoding.Type.REQUEST));
        filter.setForceResponseEncoding(encoding.shouldForce(org.springframework.boot.web.servlet.server.Encoding.Type.RESPONSE));
        return filter;
    }

    /**
     * Undertow调优
     */

//    @Bean
//    public WebServerFactoryCustomizer<UndertowServletWebServerFactory> webServerFactoryCustomizer() {
//        return factory -> {
//            factory.addBuilderCustomizers(builder -> {
//                //[坑]客户端如果与服务端保持连接, 不断开连接的话, 服务端的XNIO task线程会被挂住, 所有task都挂住后服务端就不会响应任何请求了
//                //jstack可见task线程RUNNABLE 一直在poll0, 正常应该是WAITING状态
//                //配置以后, 一旦超时会抛出 org.xnio.channels.ReadTimeoutException org.xnio.channels.WriteTimeoutException 异常
//                builder.setSocketOption(Options.READ_TIMEOUT, 60000)
//                        .setSocketOption(Options.WRITE_TIMEOUT, 60000);
//            });
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
//                                        .addHttpMethod("OPTIONS") // CORS 跨域有用
//                                        .addHttpMethod("PUT")
//                        )
//                );
//            });
//        };
//    }

    /**
     * 添加MVC拦截器
     */

    @Bean
    public WebMvcConfigurer webMvcConfigurer(BasicHandlerInterceptor basicHandlerInterceptor){
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(basicHandlerInterceptor);
            }
        };
    }

    /**
     * GlaciHttpClient
     */

//    @Bean
//    public GlaciHttpClient glaciHttpClient() {
//        return new GlaciHttpClient().setHosts("http://localhost:8000");
//    }
//    @Bean
//    public SimpleOkHttpClient glaciHttpClient() {
//        return (SimpleOkHttpClient) new SimpleOkHttpClient().setHosts("http://localhost:8000");
//    }

}
