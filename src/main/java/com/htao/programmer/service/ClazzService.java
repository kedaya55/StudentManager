package com.htao.programmer.service;

import com.htao.programmer.entity.Clazz;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: kedaya55
 * @Date: 2019-12-17 18:39
 */
@Service
public interface ClazzService {
    /**
     * 添加班级
     * @param clazz
     * @return
     */
    int add(Clazz clazz);

    /**
     * 查找班级列表
     * @param queryMap
     * @return
     */
    List<Clazz> findList(Map<String, Object> queryMap);

    /**
     * 搜索总记录
     * @param queryMap
     * @return
     */
    int getTotal(Map<String, Object> queryMap);


    /**
     * 修改班级
     * @param clazz
     * @return
     */
    int edit(Clazz clazz);

    /**
     * 删除班级
     * @param idsString
     * @return
     */
    int delete(String idsString);

    /**
     * 根据班级名查找班级
     * @param name
     * @return
     */
    Clazz findByName(String name);

    /**
     * 查询所有班级
     * @return
     */
    List<Clazz> findAll();
}
