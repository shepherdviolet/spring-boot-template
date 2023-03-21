package com.github.shepherdviolet.webdemo.demo.mybatis.dao;

import com.github.shepherdviolet.webdemo.demo.mybatis.entity.User;

import java.util.List;

public interface UserDao {

    int insert(User record);

    List<User> select();

}
