package com.github.shepherdviolet.webdemo.demo.shorturl.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 短链接服务示例
 */
@RestController
public class ShortUrlController {

    private static final Logger logger = LoggerFactory.getLogger(ShortUrlController.class);

    /**
     * http://localhost:8000/s/1Au7Zx
     */
    @RequestMapping({"/s/{shortUrl}"})
    public byte[] resolveShortUrl(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        response.sendRedirect("https://www.baidu.com");
        return null;
    }

}
