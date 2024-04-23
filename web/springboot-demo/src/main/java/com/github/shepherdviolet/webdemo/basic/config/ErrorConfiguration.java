package com.github.shepherdviolet.webdemo.basic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 异常处理配置类
 */
@Configuration
//扫包路径. 如果不指定包路径(@ComponentScan()), Spring会扫描当前类所在包路径下所有的组件(包括子包)
@ComponentScan({
        "com.github.shepherdviolet.webdemo.basic.error",
})
public class ErrorConfiguration {

    @Value("classpath:${messages.error-code.basename:msg/error-code}")
    private String errorCodeBaseName;

    @Value("classpath:${messages.error-desc.basename:msg/error-desc}")
    private String errorDescBaseName;

    /**
     * 错误码翻译文件: msg/error-code.properties msg/error-code_zh_CN.properties ......
     */
    @Bean("errorCodeMessageSource")
    public MessageSource errorCodeMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(errorCodeBaseName);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    /**
     * 错误描述翻译文件: msg/error-desc.properties msg/error-desc_zh_CN.properties ......
     */
    @Bean("errorDescMessageSource")
    public MessageSource errorDescMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(errorDescBaseName);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

}
