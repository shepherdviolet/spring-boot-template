package com.github.shepherdviolet.webdemo.demo.micrometer.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Micrometer示例
 */
@Configuration
@ComponentScan({
        "com.github.shepherdviolet.webdemo.demo.micrometer.controller",
})
public class MicrometerConfiguration {

}
