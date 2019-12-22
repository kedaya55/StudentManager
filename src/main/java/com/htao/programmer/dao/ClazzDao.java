package com.htao.programmer.dao;

import com.htao.programmer.entity.Clazz;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: kedaya55
 * @Date: 2019-12-18 20:20
 */
@Repository
public interface ClazzDao {

    @Update("insert into clazz(gradeId,name,remark) values(#{gradeId},#{name},#{remark})")
    /**
     * 添加班级
     * @param clazz
     * @return
     */
    int add(Clazz clazz);

    @Select("select * from clazz where name like #{name} limit #{offset} , #{pageSize}")
    /**
     * 查找班级列表
     * @param queryMap
     * @return
     */
    List<Clazz> findList(Map<String, Object> queryMap);

    @Select("select count(id) from clazz where name like #{name}")
    /**
     * 搜索总记录
     * @param queryMap
     * @return
     */
    int getTotal(Map<String, Object> queryMap);


    @Update("update clazz set gradeId = #{gradeId} ,name = #{name} ,remark = #{remark} where id = #{id}")
    /**
     * 修改班级
     * @param clazz
     * @return
     */
    int edit(Clazz clazz);

    @Update("delete from clazz where id in (${value})")
    /**
     * 删除班级
     * @param idsString
     * @return
     */
    int delete(String idsString);

    @Select("select * from clazz where name = #{name} ")
    /**
     * 根据班级名查找班级
     * @param name
     * @return
     */
    Clazz findByName(String name);

    @Select("select * from clazz")
    /**
     * 查询所有班级
     * @return
     */
    List<Clazz> findAll();
}
