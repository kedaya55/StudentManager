package com.htao.programmer.dao;

import com.htao.programmer.entity.Grade;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: kedaya55
 * @Date: 2019-12-17 18:41
 */
@Repository
public interface GradeDao {

    @Select("select * from grade where name like #{name} limit #{offset} , #{pageSize}")
    /**
     * 查找年级列表
     * @param queryMap
     * @return
     */
    List<Grade> findList(Map<String,Object> queryMap);

    @Select("select count(id) from grade where name like #{name}")
    /**
     * 搜索总记录
     * @param queryMap
     * @return
     */
    int getTotal(Map<String, Object> queryMap);

    @Select("select * from grade where name = #{name} ")
    /**
     * 根据年级名查找年级
     * @param name
     * @return
     */
    Grade finByName(String name);

    @Update("insert into grade(name,remark) values(#{name},#{remark})")
    /**
     * 添加年级
     * @param grade
     * @return
     */
    int add(Grade grade);

    @Update("update grade set name = #{name} ,remark = #{remark} where id = #{id}")
    /**
     * 编辑年级信息
     * @param grade
     * @return
     */
    int edit(Grade grade);

    @Update("delete from grade where id in (${value})")
    /**
     * 删除年级信息
     * @param idsString
     * @return
     */
    int delete(String idsString);

    @Select("select * from grade")
    /**
     * 查询所有年级
     * @return
     */
    List<Grade> findAll();
}
