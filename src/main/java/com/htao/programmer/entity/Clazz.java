package com.htao.programmer.entity;

/**
 * @Author: kedaya55
 * @Date: 2019-12-17 18:37
 */

public class Clazz {
    private Long id;
    private Long gradeId;
    private String name;
    private String remark;


    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "id=" + id +
                ", gradeId=" + gradeId +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
