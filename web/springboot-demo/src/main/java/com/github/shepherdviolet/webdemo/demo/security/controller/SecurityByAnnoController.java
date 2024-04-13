package com.github.shepherdviolet.webdemo.demo.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SpringSecurity示例, @PreAuthorize注解需要@EnableGlobalMethodSecurity(prePostEnabled = true)开启!
 */
@RestController
@RequestMapping("/security/byanno")
public class SecurityByAnnoController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * http://localhost:8000/security/byanno/admin
     * admin/admin
     */
    @RequestMapping("/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String admin() {
        return "ok";
    }

    /**
     * http://localhost:8000/security/byanno/user
     * user1/user1
     */
    @RequestMapping("/user")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String user() {
        return "ok";
    }

    /**
     * http://localhost:8000/security/byanno/useradmin
     * admin/admin
     * user1/user1
     */
    @RequestMapping("/useradmin")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public String useradmin() {
        return "ok";
    }

    /**
     * http://localhost:8000/security/byanno/any
     */
    @RequestMapping("/any")
    public String any() {
        return "ok";
    }

}
