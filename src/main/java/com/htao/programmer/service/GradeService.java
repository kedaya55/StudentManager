package com.htao.programmer.service;

import com.htao.programmer.entity.Grade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: kedaya55
 * @Date: 2019-12-17 18:39
 */
@Service
public interface GradeService {
    /**
     * 添加年级
     * @param grade
     * @return
     */
    int add(Grade grade);

    /**
     * 查找年级列表
     * @param queryMap
     * @return
     */
    List<Grade> findList(Map<String,Object> queryMap);

    /**
     * 搜索总记录
     * @param queryMap
     * @return
     */
    int getTotal(Map<String,Object> queryMap);


    /**
     * 修改年级
     * @param grade
     * @return
     */
    int edit(Grade grade);

    /**
     * 删除年级
     * @param idsString
     * @return
     */
    int delete(String idsString);

    /**
     * 根据年级名查找年级
     * @param name
     * @return
     */
    Grade findByName(String name);

    /**
     * 查询所有年级
     * @return
     */
    List<Grade> findAll();
}
