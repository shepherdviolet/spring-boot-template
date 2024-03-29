package com.github.shepherdviolet.webdemo.demo.common.config;

import com.github.shepherdviolet.glacimon.spring.config.property.OptionalYamlPropertySourceFactory;
import com.github.shepherdviolet.glacimon.spring.x.crypto.cryptoprop.EnableCryptoProp;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;

/**
 * <p>配置文件加载示例(yml/properties)</p>
 *
 * <p>通过PropertySource或PropertySourcesPlaceholderConfigurer加载的配置优先级比application.yaml和启动参数都低,
 * 但是除了spring几个基础参数以外, 其他的第三方库的参数都能生效</p>
 */
@Configuration
//启用ConfigurationProperties(允许配置绑定到Bean上), 见com.github.shepherdviolet.webdemo.demo.properties.prop包中的配置类
@EnableConfigurationProperties
@ComponentScan({
        "com.github.shepherdviolet.webdemo.demo.common.controller",
        "com.github.shepherdviolet.webdemo.demo.common.prop"
})
//加载配置文件到Spring的Environment中(这个注解不能用通配符), 加载的参数用${...}获取, 配置多个文件时, 上面的优先度低, 下面的优先度高
@PropertySource({
        "classpath:config/demo/common/properties/general.properties",
})
//加载yaml文件到Spring的Environment中(这个注解不能用通配符), 加载的参数用${...}获取, 配置多个文件时, 上面的优先度低, 下面的优先度高
@PropertySource(factory = OptionalYamlPropertySourceFactory.class, value = {
        "classpath:config/demo/common/properties/yaml.yml",
})
//启用属性解密
@EnableCryptoProp
public class PropertiesConfiguration {

    /**
     * 把指定properties文件加载为"Properties"实例中
     * 注意: 文件中的属性不会加载到Spring Environment中去
     */
    @Bean(name = "myStandaloneProperties")
    public static PropertiesFactoryBean myProperties() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocations(new ClassPathResource("config/demo/common/properties/standalone.properties"));
        propertiesFactoryBean.setIgnoreResourceNotFound(true);
        propertiesFactoryBean.setFileEncoding("UTF-8");
        return propertiesFactoryBean;
    }

}
