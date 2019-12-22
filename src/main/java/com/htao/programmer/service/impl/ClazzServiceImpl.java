package com.htao.programmer.service.impl;

import com.htao.programmer.dao.ClazzDao;
import com.htao.programmer.entity.Clazz;
import com.htao.programmer.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: kedaya55
 * @Date: 2019-12-18 19:53
 */
@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzDao clazzDao;

    @Override
    public int add(Clazz clazz) {
        return clazzDao.add(clazz);
    }

    @Override
    public List<Clazz> findList(Map<String, Object> queryMap) {
        return clazzDao.findList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return clazzDao.getTotal(queryMap);
    }

    @Override
    public int edit(Clazz clazz) {
        return clazzDao.edit(clazz);
    }

    @Override
    public int delete(String idsString) {
        return clazzDao.delete(idsString);
    }

    @Override
    public Clazz findByName(String name) {
        return clazzDao.findByName(name);
    }

    @Override
    public List<Clazz> findAll() {
        return clazzDao.findAll();
    }
}
