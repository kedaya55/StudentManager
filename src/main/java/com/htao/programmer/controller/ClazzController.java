package com.htao.programmer.controller;

import com.htao.programmer.entity.Clazz;
import com.htao.programmer.page.Page;
import com.htao.programmer.service.ClazzService;
import com.htao.programmer.service.GradeService;
import net.sf.json.JSONArray;
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
@RequestMapping("/clazz")
@Controller
public class ClazzController {

    @Autowired
    private GradeService gradeService;
    @Autowired
    private ClazzService clazzService;


    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        model.setViewName("clazz/clazz_list");
        model.addObject("gradeList",gradeService.findAll());
        model.addObject("gradeListJson", JSONArray.fromObject(gradeService.findAll()));
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
        map.put("rows", clazzService.findList(queryMap));
        map.put("total",clazzService.getTotal(queryMap));
        return map;
    }

    /**
     * 添加班级
     * @param clazz
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Clazz clazz){
        Map<String,String> map = new HashMap<>();
        if (clazz == null){
            map.put("type","error");
            map.put("msg","数据绑定出错，请联系开发作者!");
            return map;
        }
        if (StringUtils.isEmpty(clazz.getName())){
            map.put("type","error");
            map.put("msg","用户名不能为空!");
            return map;
        }
        if (clazz.getGradeId() == null){
            map.put("type","error");
            map.put("msg","请选择所属年级!");
            return map;
        }
       Clazz exitClazz = clazzService.findByName(clazz.getName());
        if (exitClazz != null){
            map.put("type","error");
            map.put("msg","班级已存在!");
            return map;
        }
        if (clazzService.add(clazz) <= 0){
            map.put("type","error");
            map.put("msg","添加失败!");
            return map;
        }
        map.put("type","success");
        map.put("msg","添加成功!");
        return map;
    }

    /**
     * 编辑班级
     * @param clazz
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> edit(Clazz clazz){
        Map<String,String> map = new HashMap<>();
        if (clazz == null){
            map.put("type","error");
            map.put("msg","数据绑定出错，请联系开发作者!");
            return map;
        }
        if (StringUtils.isEmpty(clazz.getName())){
            map.put("type","error");
            map.put("msg","班级不能为空!");
            return map;
        }
        if (clazz.getGradeId() == null){
            map.put("type","error");
            map.put("msg","所属年级不能为空!");
            return map;
        }
        Clazz exitClazz = clazzService.findByName(clazz.getName());
        if (exitClazz != null){
            if (!clazz.getId().equals(exitClazz.getId())){
                map.put("type","error");
                map.put("msg","班级已存在!");
                return map;
            }
        }
        if (clazzService.edit(clazz) <= 0){
            map.put("type","error");
            map.put("msg","编辑失败!");
            return map;
        }
        map.put("type","success");
        map.put("msg","编辑成功!");
        return map;
    }

    /**
     * 删除班级
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
        try {
            if (clazzService.delete(idsString)<=0){
                map.put("type","error");
                map.put("msg","删除失败!");
                return map;
            }
        }catch (Exception e){
            map.put("type","error");
            map.put("msg","该班级下存在学生信息，请勿删除!");
            return map;
        }
        map.put("type","success");
        map.put("msg","删除成功!");
        return map;
    }

}
