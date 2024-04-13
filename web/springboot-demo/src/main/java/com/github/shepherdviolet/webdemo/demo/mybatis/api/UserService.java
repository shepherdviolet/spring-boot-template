package com.github.shepherdviolet.webdemo.demo.mybatis.api;

import com.github.shepherdviolet.webdemo.demo.mybatis.dto.UserDto;

import java.util.List;

public interface UserService {

    int addUser(UserDto user);

    List<UserDto> getAllUsers();

}
