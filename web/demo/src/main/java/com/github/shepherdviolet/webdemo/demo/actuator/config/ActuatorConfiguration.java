package com.github.shepherdviolet.webdemo.demo.actuator.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Actuator端点示例
 */
@Configuration
@ComponentScan({
        "com.github.shepherdviolet.webdemo.demo.actuator.controller",
        "com.github.shepherdviolet.webdemo.demo.actuator.component",
})
public class ActuatorConfiguration {

}
