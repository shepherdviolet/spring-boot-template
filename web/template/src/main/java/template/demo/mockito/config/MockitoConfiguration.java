package template.demo.mockito.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 文件上传示例
 *
 * @author S.Violet
 */
@Configuration
@ComponentScan({
        "template.demo.mockito.service",
})
public class MockitoConfiguration {

}
