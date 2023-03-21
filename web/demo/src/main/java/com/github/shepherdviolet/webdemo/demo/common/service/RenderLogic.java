package com.github.shepherdviolet.webdemo.demo.common.service;

import org.springframework.stereotype.Service;

/**
 * 处理逻辑
 */
@Service("renderLogic")
public class RenderLogic implements Logic {

    @Override
    public String handle(String input) {
        return input + " Rendered";
    }

}
