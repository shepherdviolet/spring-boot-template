package com.github.shepherdviolet.webdemo.core.config;

import com.github.shepherdviolet.glacimon.java.collections.LambdaBuildable;
import com.github.shepherdviolet.glacimon.spring.x.monitor.txtimer.def.EnableTxTimerSpringConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 基本模板配置类
 */
@Configuration
@EnableTxTimerSpringConfig
//扫包路径. 如果不指定包路径(@ComponentScan()), Spring会扫描当前类所在包路径下所有的组件(包括子包)
@ComponentScan({
        "com.github.shepherdviolet.webdemo.core.controller",
})
public class CoreConfiguration implements LambdaBuildable {

    /**
     * GlaciHttpClient
     */
//    @Bean
//    public GlaciHttpClient glaciHttpClient() {
//        return new GlaciHttpClient().setHosts("http://localhost:8000");
//    }
//    @Bean
//    public SimpleOkHttpClient glaciHttpClient() {
//        return (SimpleOkHttpClient) new SimpleOkHttpClient().setHosts("http://localhost:8000");
//    }

}
