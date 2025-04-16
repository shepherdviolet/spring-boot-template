package com.github.shepherdviolet.webdemo.demo.actuator.component;

import com.github.shepherdviolet.glacimon.java.collections.LambdaBuilder;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Actuator 信息端点示例
 */
@Component
public class DemoInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("startup", LambdaBuilder.hashMap(m -> {
            m.put("start-time", new Date());
        }));
    }

}