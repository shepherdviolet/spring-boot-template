package template.demo.mybatis.api;

import template.demo.mybatis.entity.User;

import java.util.List;

public interface UserService {

    int addUser(User user);

    List<User> getAllUsers();

}
