package com.htao.programmer.controller;

import com.htao.programmer.entity.Grade;
import com.htao.programmer.entity.User;
import com.htao.programmer.page.Page;
import com.htao.programmer.service.GradeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: kedaya55
 * @Date: 2019-12-17 18:02
 */
@RequestMapping("/grade")
@Controller
public class GradeController {

    @Autowired
    private GradeService gradeService;


    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        model.setViewName("grade/grade_list");
        return model;
    }

    @RequestMapping(value = "/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "name",required = false,defaultValue = "") String name,
            Page page
    ){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("name","%"+name+"%");
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());
        map.put("rows", gradeService.findList(queryMap));
        map.put("total",gradeService.getTotal(queryMap));
        return map;
    }

    /**
     * 添加年级
     * @param grade
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Grade grade){
        Map<String,String> map = new HashMap<>();
        if (grade == null){
            map.put("type","error");
            map.put("msg","数据绑定出错，请联系开发作者!");
            return map;
        }
        if (StringUtils.isEmpty(grade.getName())){
            map.put("type","error");
            map.put("msg","用户名不能为空!");
            return map;
        }
       Grade exitGrade = gradeService.findByName(grade.getName());
        if (exitGrade != null){
            map.put("type","error");
            map.put("msg","年级已存在!");
            return map;
        }
        if (gradeService.add(grade) <= 0){
            map.put("type","error");
            map.put("msg","添加失败!");
            return map;
        }
        map.put("type","success");
        map.put("msg","添加成功!");
        return map;
    }

    /**
     * 编辑年级
     * @param grade
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> edit(Grade grade){
        Map<String,String> map = new HashMap<>();
        if (grade == null){
            map.put("type","error");
            map.put("msg","数据绑定出错，请联系开发作者!");
            return map;
        }
        if (StringUtils.isEmpty(grade.getName())){
            map.put("type","error");
            map.put("msg","用户名不能为空!");
            return map;
        }
        Grade exitGrade = gradeService.findByName(grade.getName());
        if (exitGrade != null){
            if (!grade.getId().equals(exitGrade.getId())){
                map.put("type","error");
                map.put("msg","用户已存在!");
                return map;
            }
        }
        if (gradeService.edit(grade) <= 0){
            map.put("type","error");
            map.put("msg","编辑失败!");
            return map;
        }
        map.put("type","success");
        map.put("msg","编辑成功!");
        return map;
    }

    /**
     * 删除年级
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> delete(
            @RequestParam(value = "ids[]",required = true) Long[] ids
    ) {
        Map<String, String> map = new HashMap<>();
        String idsString = "";
        for (Long id:ids){
            idsString += id+",";
        }
        idsString = idsString.substring(0,idsString.length()-1);
        try{
            if (gradeService.delete(idsString)<=0){
                map.put("type","error");
                map.put("msg","删除失败!");
                return map;
            }
        }catch (Exception e){
            map.put("type","success");
            map.put("msg","该年级下存在班级信息，请勿删除!");
        }

        map.put("type","success");
        map.put("msg","删除成功!");
        return map;
    }

}
