// <replace-by> package ${java_package}.infra.config;
package __java_package__.infra.config;

import com.github.shepherdviolet.glacimon.java.collections.LambdaBuildable;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * WEB调优
 */
@Configuration
public class WebConfiguration implements LambdaBuildable {

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

}
