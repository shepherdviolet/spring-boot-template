package com.github.shepherdviolet.webdemo.demo.properties.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import sviolet.slate.common.spring.property.OptionalYamlPropertySourceFactory;

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
        "com.github.shepherdviolet.webdemo.demo.properties.controller",
        "com.github.shepherdviolet.webdemo.demo.properties.prop"
})
//加载配置文件到Spring的Environment中(这个注解不能用通配符), 加载的参数用${...}获取, 配置多个文件时, 上面的优先度低, 下面的优先度高
@PropertySource({
        "classpath:config/demo/properties/general.properties",
})
//加载yaml文件到Spring的Environment中(这个注解不能用通配符), 加载的参数用${...}获取, 配置多个文件时, 上面的优先度低, 下面的优先度高
@PropertySource(factory = OptionalYamlPropertySourceFactory.class, value = {
        "classpath:config/demo/properties/yaml.yml",
})
public class PropertiesConfiguration {

//    /**
//     * 加载自定义配置文件, PropertySourcesPlaceholderConfigurer在上下文中只有一个有效, 建议用PropertySource
//     */
//    @Bean
//    public static PropertySourcesPlaceholderConfigurer properties() {
//        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
//        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
//        yamlPropertiesFactoryBean.setResources(new ClassPathResource("config/demo/properties/yaml.yml"));
////        yamlPropertiesFactoryBean.setResources(new FileSystemResource("E:\\__Temp\\config.yml"));
//        configurer.setProperties(yamlPropertiesFactoryBean.getObject());
//        return configurer;
//    }

}
