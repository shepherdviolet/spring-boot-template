package template.demo.mybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import template.demo.mybatis.api.UserService;
import template.demo.mybatis.dao.UserDao;
import template.demo.mybatis.entity.User;

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
