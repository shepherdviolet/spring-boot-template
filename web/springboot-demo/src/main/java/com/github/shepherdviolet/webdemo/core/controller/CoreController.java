package com.github.shepherdviolet.webdemo.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 基本模板Controller
 *
 * @author S.Violet
 */
@Controller
@RequestMapping("/core")
public class CoreController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * NCR编码转换:unicode
     * http://localhost:8000/core/ncr/unicode
     */
    @RequestMapping("/ncr/unicode")
    public String ncrUnicode() {
        return "core/ncr/unicode";
    }

    /**
     * NCR编码转换:utf-8
     * http://localhost:8000/core/ncr/utf8
     */
    @RequestMapping("/ncr/utf8")
    public String ncrUtf8() {
        return "core/ncr/utf8";
    }

    /**
     * NCR编码转换:ascii
     * http://localhost:8000/core/ncr/ascii
     */
    @RequestMapping("/ncr/ascii")
    public String ncrAscii() {
        return "core/ncr/ascii";
    }

    /**
     * NCR编码转换:urlEncode
     * http://localhost:8000/core/ncr/url
     */
    @RequestMapping("/ncr/url")
    public String ncrUrl() {
        return "core/ncr/urlEncode";
    }

}
