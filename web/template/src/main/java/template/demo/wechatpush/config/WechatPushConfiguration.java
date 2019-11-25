package template.demo.wechatpush.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 微信推送服务示例
 */
@Configuration
@ComponentScan({
        "template.demo.wechatpush.controller",
        "template.demo.wechatpush.service",
})
public class WechatPushConfiguration {

}
