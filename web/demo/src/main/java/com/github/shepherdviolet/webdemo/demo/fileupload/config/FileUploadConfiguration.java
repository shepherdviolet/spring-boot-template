package com.github.shepherdviolet.webdemo.demo.fileupload.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 文件上传示例
 */
@Configuration
@ComponentScan({
        "com.github.shepherdviolet.webdemo.demo.fileupload.controller",
})
public class FileUploadConfiguration {

}
