package com.github.shepherdviolet.webdemo.demo.shorturl.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 短链接服务示例
 */
@Configuration
@ComponentScan({
        "com.github.shepherdviolet.webdemo.demo.shorturl.controller",
})
public class ShortUrlConfiguration {

}
