package template.demo.mybatis.dao;

import template.demo.mybatis.entity.User;

import java.util.List;

public interface UserDao {

    int insert(User record);

    List<User> select();

}
