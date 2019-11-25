// <replace-by> package ${java_package};
package __java_package__;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
        // <replace-by>         "${java_package}.basic.config"
        "__java_package__.basic.config"
})
// <replace-by> public class ${application_class} {
public class __application_class__ {

    public static void main(String[] args) {
        // <replace-by>         SpringApplication.run(${application_class}.class, args);
        SpringApplication.run(__application_class__.class, args);
    }

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

}
