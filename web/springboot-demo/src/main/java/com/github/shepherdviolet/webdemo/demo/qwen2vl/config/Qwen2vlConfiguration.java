package com.github.shepherdviolet.webdemo.demo.qwen2vl.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Qwen2vl对接示例(HTTP API)</p>
 */
@Configuration
@ComponentScan({
        "com.github.shepherdviolet.webdemo.demo.qwen2vl.controller",
})
public class Qwen2vlConfiguration {

}
