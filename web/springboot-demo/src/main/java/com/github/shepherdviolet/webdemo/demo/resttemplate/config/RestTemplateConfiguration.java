package com.github.shepherdviolet.webdemo.demo.resttemplate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate示例
 */
@Configuration
@ComponentScan({
        "com.github.shepherdviolet.webdemo.demo.resttemplate.controller",
})
public class RestTemplateConfiguration {

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory(){
        JdkClientHttpRequestFactory clientHttpRequestFactory = new JdkClientHttpRequestFactory();
        clientHttpRequestFactory.setReadTimeout(20000);
        return clientHttpRequestFactory;
    }

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        return new RestTemplate(clientHttpRequestFactory);
    }

}
