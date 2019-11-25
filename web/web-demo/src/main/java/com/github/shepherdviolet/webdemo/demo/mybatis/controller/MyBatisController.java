package com.github.shepherdviolet.webdemo.demo.mybatis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.shepherdviolet.webdemo.demo.mybatis.api.UserService;
import com.github.shepherdviolet.webdemo.demo.mybatis.entity.User;

/**
 * mybatis示例
 *
 * @author S.Violet
 */
@RestController
@RequestMapping("/mybatis")
public class MyBatisController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    /**
     * http://localhost:8080/mybatis/user/add?username=wang&password=123456&phone=13011112222
     */
    @RequestMapping("/user/add")
    public String userAdd(@RequestParam String username, @RequestParam String password, @RequestParam(required = false) String phone){
        logger.info("user add");

        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        user.setPhone(phone);

//        DynamicDataSource.selectDataSource("dataSource2");
        return String.valueOf(userService.addUser(user));
    }

    /**
     * http://localhost:8080/mybatis/user/get
     */
    @RequestMapping("/user/get")
    public String userGet(){
        logger.info("user get");

//        DynamicDataSource.selectDataSource("dataSource2");
        return String.valueOf(userService.getAllUsers());
    }

}
