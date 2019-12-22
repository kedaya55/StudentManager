package com.htao.programmer.service;

import com.htao.programmer.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: kedaya55
 * @Date: 2019-12-21 12:35
 */
@Service
public interface StudentService {
    /**
     * 根据学生姓名查找学生
     * @param username
     * @return
     */
    Student findByName(String username);

    /**
     * 添加学生
     * @param student
     * @return
     */
    int add(Student student);

    /**
     * 查找学生列表
     * @param queryMap
     * @return
     */
    List<Student> findList(Map<String, Object> queryMap);

    /**
     * 搜索总记录
     * @param queryMap
     * @return
     */
    int getTotal(Map<String, Object> queryMap);

    /**
     * 修改学生信息
     * @param student
     * @return
     */
    int edit(Student student);

    /**
     * 删除学生信息
     * @param idsString
     * @return
     */
    int delete(String idsString);
}
