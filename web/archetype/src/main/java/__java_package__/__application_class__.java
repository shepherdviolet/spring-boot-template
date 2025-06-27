// <replace-by> package ${java_package};
package __java_package__;

import com.github.shepherdviolet.glacimon.java.collections.LambdaBuildable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                // <replace-by>                 "${java_package}.infra.config",
                "__java_package__.infra.config",
                // <replace-by>                 "${java_package}.core.config",
                "__java_package__.core.config",
        }
)
// <replace-by> public class ${application_class} implements LambdaBuildable {
public class __application_class__ implements LambdaBuildable {

    public static void main(String[] args) {
        // <replace-by>         SpringApplication.run(${application_class}.class, args);
        SpringApplication.run(__application_class__.class, args);
    }

}
