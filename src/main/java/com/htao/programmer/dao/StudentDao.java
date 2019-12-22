package com.htao.programmer.dao;

import com.htao.programmer.entity.Student;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: kedaya55
 * @Date: 2019-12-21 12:37
 */
@Repository
public interface StudentDao {

    @Select("select * from student where username = #{username}")
    /**
     * 根据学生姓名查找学生
     * @param username
     * @return
     */
    Student findByName(String username);

    @Update("insert into student(clazzId,sn,username,password,sex,photo,remark) " +
            "values(#{clazzId},#{sn},#{username},#{password},#{sex},#{photo},#{remark})")
    /**
     * 添加学生
     * @param student
     * @return
     */
    int add(Student student);

    @Select("select * from student where username like #{username} limit #{offset} , #{pageSize}")
    /**
     * 查找学生列表
     * @param queryMap
     * @return
     */
    List<Student> findList(Map<String, Object> queryMap);

    @Select("select count(id) from student where username like #{username}")
    /**
     * 搜索总记录
     * @param queryMap
     * @return
     */
    int getTotal(Map<String, Object> queryMap);

    @Update("update student set username = #{username} , clazzId = #{clazzId}, sn = #{sn} ," +
            "password = #{password} ,photo = #{photo},remark = #{remark} where id = #{id}")
    /**
     *  修改学生信息
     * @param student
     * @return
     */
    int edit(Student student);

    @Update("delete from student where id in (${value})")
    /**
     * 删除学生信息
     * @param idsString
     * @return
     */
    int delete(String idsString);
}
