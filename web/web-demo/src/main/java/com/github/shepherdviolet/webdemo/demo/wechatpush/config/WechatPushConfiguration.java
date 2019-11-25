package com.github.shepherdviolet.webdemo.demo.wechatpush.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 微信推送服务示例
 */
@Configuration
@ComponentScan({
        "com.github.shepherdviolet.webdemo.demo.wechatpush.controller",
        "com.github.shepherdviolet.webdemo.demo.wechatpush.service",
})
public class WechatPushConfiguration {

}
