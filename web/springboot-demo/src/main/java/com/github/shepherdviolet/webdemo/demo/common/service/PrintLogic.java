package com.github.shepherdviolet.webdemo.demo.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 日志打印逻辑
 */
@Service("printLogic")
public class PrintLogic implements Logic {

    public static final Logger logger = LoggerFactory.getLogger(PrintLogic.class);

    @Override
    public String handle(String input) {
        logger.info("Print:" + input);
        return input;
    }

}
