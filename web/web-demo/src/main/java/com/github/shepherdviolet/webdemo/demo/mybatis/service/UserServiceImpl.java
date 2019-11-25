package com.github.shepherdviolet.webdemo.demo.mybatis.service;

import com.github.shepherdviolet.webdemo.demo.mybatis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.shepherdviolet.webdemo.demo.mybatis.api.UserService;
import com.github.shepherdviolet.webdemo.demo.mybatis.dao.UserDao;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public int addUser(User user) {
        return userDao.insert(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.select();
    }

}
