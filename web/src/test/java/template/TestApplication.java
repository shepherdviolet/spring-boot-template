package template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 测试入口
 *
 * @author Sviolet
 */
@SpringBootApplication(
//        exclude = {
//                DataSourceAutoConfiguration.class//排除数据库配置(可选)
//        }
)
//扫包路径
@ComponentScan({
        "template.demo.mockito.config",
})
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
