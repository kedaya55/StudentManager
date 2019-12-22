package com.htao.programmer.dao;

import com.htao.programmer.entity.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: kedaya55
 * @Date: 2019-12-12 21:46
 */
@Repository
public interface UserDao {

    @Select("select * from user where username = #{username} ")
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findByUserName(String username);

    @Update("insert into user(username,password) values(#{username},#{password})")
    /**
     * 添加用户
     * @param user
     * @return
     */
    int add(User user);

    @Select("select * from user where username like #{username} limit #{offset} , #{pageSize}")
    /**
     * 查找用户列表
     * @param queryMap
     * @return
     */
    List<User> findList(Map<String,Object> queryMap);

    @Select("select count(id) from user where username like #{username}")
    /**
     * 搜索总记录
     * @param queryMap
     * @return
     */
    int getTotal(Map<String, Object> queryMap);

    @Update("update user set username = #{username} ,password = #{password} where id = #{id}")
    /**
     * 修改用户
     * @param user
     * @return
     */
    int edit(User user);


    @Update("delete from user where id in (${value})")
    /**
     * 删除用户
     * @param idsString
     * @return
     */
    int delete(String idsString);
}
