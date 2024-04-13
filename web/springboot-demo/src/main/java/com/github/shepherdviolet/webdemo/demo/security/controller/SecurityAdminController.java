package com.github.shepherdviolet.webdemo.demo.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SpringSecurity示例
 */
@RestController
@RequestMapping("/security/admin")
public class SecurityAdminController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * http://localhost:8000/security/admin/needauth
     * admin/admin
     */
    @RequestMapping("/needauth")
    public String needauth() {
        return "ok";
    }

}
