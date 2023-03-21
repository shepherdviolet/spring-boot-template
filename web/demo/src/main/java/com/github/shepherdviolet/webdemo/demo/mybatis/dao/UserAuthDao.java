package com.github.shepherdviolet.webdemo.demo.mybatis.dao;

import com.github.shepherdviolet.webdemo.demo.mybatis.entity.UserAuth;

import java.util.List;

public interface UserAuthDao {

    int insert(UserAuth record);

    List<UserAuth> select();

}
