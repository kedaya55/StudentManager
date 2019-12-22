package com.htao.programmer.service.impl;

import com.htao.programmer.dao.GradeDao;
import com.htao.programmer.entity.Grade;
import com.htao.programmer.entity.User;
import com.htao.programmer.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: kedaya55
 * @Date: 2019-12-17 18:45
 */
@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeDao gradeDao;

    @Override
    public int add(Grade grade) {
        return gradeDao.add(grade);
    }

    @Override
    public List<Grade> findList(Map<String, Object> queryMap) {
        return gradeDao.findList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return gradeDao.getTotal(queryMap);
    }

    @Override
    public List<Grade> findAll() {
        return gradeDao.findAll();
    }

    @Override
    public int edit(Grade grade) {
        return gradeDao.edit(grade);
    }

    @Override
    public int delete(String idsString) {
        return gradeDao.delete(idsString);
    }

    @Override
    public Grade findByName(String name) {
        return gradeDao.finByName(name);
    }
}
