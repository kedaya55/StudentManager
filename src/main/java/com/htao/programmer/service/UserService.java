package com.htao.programmer.service;

import com.htao.programmer.entity.User;

import java.util.List;
import java.util.Map;


/**
 * @Author: kedaya55
 * @Date: 2019-12-12 22:15
 */

public interface UserService {
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findByUserName(String username);

    /**
     * 添加用户
     * @param user
     * @return
     */
    int add(User user);

    /**
     * 查找用户列表
     * @param queryMap
     * @return
     */
    List<User> findList(Map<String,Object> queryMap);

    /**
     * 搜索总记录
     * @param queryMap
     * @return
     */
    int getTotal(Map<String,Object> queryMap);


    /**
     * 修改用户
     * @param user
     * @return
     */
    int edit(User user);

    /**
     * 删除用户
     * @param idsString
     * @return
     */
    int delete(String idsString);
}
