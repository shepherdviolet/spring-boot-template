package com.github.shepherdviolet.webdemo.demo.mybatis.api;

import com.github.shepherdviolet.webdemo.demo.mybatis.entity.User;

import java.util.List;

public interface UserService {

    int addUser(User user);

    List<User> getAllUsers();

}
