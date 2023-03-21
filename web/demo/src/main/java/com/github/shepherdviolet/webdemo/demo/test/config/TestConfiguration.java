package com.github.shepherdviolet.webdemo.demo.test.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Springboot test 示例
 *
 * @author S.Violet
 */
@Configuration
@ComponentScan({
        "com.github.shepherdviolet.webdemo.demo.test.service",
        "com.github.shepherdviolet.webdemo.demo.test.controller",
})
public class TestConfiguration {

}
