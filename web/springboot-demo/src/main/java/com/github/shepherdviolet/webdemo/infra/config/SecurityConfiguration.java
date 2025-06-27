package com.github.shepherdviolet.webdemo.infra.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;

/**
 * 安全
 */
@Configuration
public class SecurityConfiguration {

    /**
     * 修复spring-web 5.3.39 的安全漏洞 CVE-2016-1000027
     * 1.修复该漏洞需要升级spring 6.0, 但必须要JDK17
     * 2.只要不用HttpInvokerServiceExporter就不会触发该漏洞 (不使用Spring的RemoteInvocation特性（HttpInvoker、RMI、Hessian、Burlap...）
     */
    @Bean
    @SuppressWarnings("deprecation")
    public Runnable checkCve20161000027(ObjectProvider<HttpInvokerServiceExporter> httpInvokerServiceExporters) {
        if (httpInvokerServiceExporters.stream().findAny().isPresent()) {
            throw new IllegalStateException("Detected usage of HttpInvokerServiceExporter, which is prohibited due to known security vulnerabilities (CVE-2016-1000027).");
        }
        return null;
    }

}
