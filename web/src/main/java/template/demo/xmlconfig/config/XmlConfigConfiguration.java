package template.demo.xmlconfig.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import template.demo.xmlconfig.entity.XmlBeanWrapper;
import template.demo.xmlconfig.entity.XmlBean;

/**
 * XML方式配置示例
 *
 * @author S.Violet
 */
@Configuration
@ComponentScan({
        "template.demo.xmlconfig.controller",
})
//引入XML配置, classpath*支持从其他JAR包中加载XML, 如果加载不到, 请仔细检查路径是否正确!!!
@ImportResource({
        "classpath*:config/demo/xmlconfig/*.xml",
})
public class XmlConfigConfiguration {

    /**
     * 注解方式指定注入对象的类名
     */
    @Bean
    public XmlBeanWrapper xmlBeanWrapper(@Qualifier("xmlBean2") XmlBean xmlBean) {
        return new XmlBeanWrapper(xmlBean);
    }

}
