package com.github.shepherdviolet.webdemo.demo.mybatis.controller;

import com.github.shepherdviolet.glacimon.spring.helper.data.datasource.DynamicDataSource;
import com.github.shepherdviolet.webdemo.demo.mybatis.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.shepherdviolet.webdemo.demo.mybatis.api.UserService;

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
     * http://localhost:8000/mybatis/user/add?username=wang&password=123456&phone=13011112222
     * http://localhost:8000/mybatis/user/add?username=wang&password=123456&phone=13011112222&dataSource=dataSource1
     */
    @RequestMapping("/user/add")
    public String userAdd(@RequestParam String username, @RequestParam String password,
                          @RequestParam(required = false) String phone, @RequestParam(required = false) String dataSource){
        logger.info("user add");

        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setPassword(password);
        userDto.setPhone(phone);

        // 选择数据源, null的话用默认数据源, 如果未配置多数据源则代码无效; 设置以后当前线程就会用指定数据源操作数据库(一直有效, 无限次, 直到再调用本方法更改)
        DynamicDataSource.selectDataSource(dataSource);
        return String.valueOf(userService.addUser(userDto));
    }

    /**
     * http://localhost:8000/mybatis/user/get
     * http://localhost:8000/mybatis/user/get?dataSource=dataSource1
     */
    @RequestMapping("/user/get")
    public String userGet(@RequestParam(required = false) String dataSource){
        logger.info("user get");

        // 选择数据源, null的话用默认数据源, 如果未配置多数据源则代码无效; 设置以后当前线程就会用指定数据源操作数据库(一直有效, 无限次, 直到再调用本方法更改)
        DynamicDataSource.selectDataSource(dataSource);
        return String.valueOf(userService.getAllUsers());
    }

}
