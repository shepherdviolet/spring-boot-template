package com.github.shepherdviolet.webdemo.demo.resttemplate.controller;

import com.github.shepherdviolet.webdemo.demo.resttemplate.entity.SimpleRequest;
import com.github.shepherdviolet.webdemo.demo.resttemplate.entity.SimpleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

/**
 * RestTemplate示例
 */
@RestController
@RequestMapping("/resttemplate/client")
public class RestClientController {

    private static final String URL_PREFIX = "http://localhost:8000/resttemplate/server";

    private static final Logger logger = LoggerFactory.getLogger(RestClientController.class);

    @Autowired
    private RestTemplate restTemplate;

    /**
     * http://localhost:8000/resttemplate/client/get
     */
    @RequestMapping("/get")
    public String get() {
        ResponseEntity<SimpleResponse> responseEntity = restTemplate.getForEntity(URL_PREFIX + "/get?name={name}&amount={amount}",
                SimpleResponse.class,
                "gettest", 123.45);
        logger.info("return status: " + responseEntity.getStatusCodeValue());
        logger.info("return headers: " + responseEntity.getHeaders());
        return String.valueOf(responseEntity.getBody());
    }

    /**
     * http://localhost:8000/resttemplate/client/post
     */
    @RequestMapping("/post")
    public String post() {
        ResponseEntity<SimpleResponse> responseEntity = restTemplate.postForEntity(URL_PREFIX + "/post",
                new SimpleRequest().setName("posttest").setAmount(new BigDecimal("678.90")),
                SimpleResponse.class);
        logger.info("return status: " + responseEntity.getStatusCodeValue());
        logger.info("return headers: " + responseEntity.getHeaders());
        return String.valueOf(responseEntity.getBody());
    }

}
