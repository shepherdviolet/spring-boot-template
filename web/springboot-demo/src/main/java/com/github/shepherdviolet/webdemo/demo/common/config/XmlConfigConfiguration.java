package com.github.shepherdviolet.webdemo.demo.common.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import com.github.shepherdviolet.webdemo.demo.common.entity.FatBean;
import com.github.shepherdviolet.webdemo.demo.common.entity.SimpleBeanImpl;

/**
 * XML方式配置示例
 *
 * @author S.Violet
 */
@Configuration
@ComponentScan({
        "com.github.shepherdviolet.webdemo.demo.common.controller",
})
//引入XML配置, classpath*支持从其他JAR包中加载XML, 如果加载不到, 请仔细检查路径是否正确!!!
@ImportResource({
        "classpath*:config/demo/common/*.xml",
})
public class XmlConfigConfiguration {

    /**
     * 注解方式指定注入对象的类名
     */
    @Bean
    @Primary // 设置为默认Bean, 当上下文中有多个FatBean时, Autowired默认注入这个
    public FatBean xmlFatBean(@Qualifier("xmlBean2") SimpleBeanImpl simpleBean) {
        return new FatBean(simpleBean);
    }

}
