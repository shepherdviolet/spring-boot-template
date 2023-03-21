package com.github.shepherdviolet.webdemo.demo.mybatis.service;

import com.github.shepherdviolet.webdemo.demo.mybatis.api.UserService;
import com.github.shepherdviolet.webdemo.demo.mybatis.dao.UserAuthDao;
import com.github.shepherdviolet.webdemo.demo.mybatis.dao.UserDao;
import com.github.shepherdviolet.webdemo.demo.mybatis.dto.UserDto;
import com.github.shepherdviolet.webdemo.demo.mybatis.entity.User;
import com.github.shepherdviolet.webdemo.demo.mybatis.entity.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserAuthDao userAuthDao;

    @Override
    @Transactional
    public int addUser(UserDto user) {
        // 此处DTO/DO映射可以用BeanUtils或者orika
        User u = new User();
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        u.setPhone(user.getPhone());
        u.setEnabled("true");

        UserAuth ua = new UserAuth();
        ua.setUsername(user.getUsername());
        ua.setAuthority("USER");

        userDao.insert(u);
        userAuthDao.insert(ua);
        return 1;
    }

    @Override
    public List<UserDto> getAllUsers() {
        // 此处DTO/DO映射可以用BeanUtils或者orika
        return Optional.ofNullable(userDao.select())
                .orElse(Collections.emptyList())
                .stream()
                .map(user -> {
                    UserDto userDto = new UserDto();
                    userDto.setUsername(user.getUsername());
                    userDto.setPassword(user.getPassword());
                    userDto.setPassword(user.getPassword());
                    return userDto;
                })
                .collect(Collectors.toList());
    }

}
