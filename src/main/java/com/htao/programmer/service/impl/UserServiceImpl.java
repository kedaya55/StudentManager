package com.htao.programmer.service.impl;

import com.htao.programmer.dao.UserDao;
import com.htao.programmer.entity.User;
import com.htao.programmer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: kedaya55
 * @Date: 2019-12-12 22:16
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User findByUserName(String username) {
        return userDao.findByUserName(username);
    }

    @Override
    public int add(User user) {
        return userDao.add(user);
    }

    @Override
    public int delete(String idsString) {
        return userDao.delete(idsString);
    }

    @Override
    public int edit(User user) {
        return userDao.edit(user);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return userDao.getTotal(queryMap);
    }

    @Override
    public List<User> findList(Map<String,Object> queryMap) {
        return userDao.findList(queryMap);
    }
}
