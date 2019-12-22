package com.htao.programmer.controller;

import com.htao.programmer.entity.User;
import com.htao.programmer.page.Page;
import com.htao.programmer.service.UserService;
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
 * 用户（管理员）控制器
 * @Author: kedaya55
 * @Date: 2019-12-15 12:18
 */
@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户管理列表页
     * @param model
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        model.setViewName("user/user_list");
        return model;
    }

    @RequestMapping(value = "/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "username",required = false,defaultValue = "") String username,
            Page page
    ){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("username","%"+username+"%");
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());
        map.put("rows",userService.findList(queryMap));
        map.put("total",userService.getTotal(queryMap));
        return map;
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(User user){
        Map<String,String> map = new HashMap<>();
        if (user == null){
            map.put("type","error");
            map.put("msg","数据绑定出错，请联系开发作者!");
            return map;
        }
        if (StringUtils.isEmpty(user.getUsername())){
            map.put("type","error");
            map.put("msg","用户名不能为空!");
            return map;
        }
        if (StringUtils.isEmpty(user.getPassword())){
            map.put("type","error");
            map.put("msg","密码不能为空!");
            return map;
        }
        User exitUser = userService.findByUserName(user.getUsername());
        if (exitUser != null){
            map.put("type","error");
            map.put("msg","用户已存在!");
            return map;
        }
        if (userService.add(user) <= 0){
            map.put("type","error");
            map.put("msg","添加失败!");
            return map;
        }
        map.put("type","success");
        map.put("msg","添加成功!");
        return map;
    }


    /**
     * 编辑用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> edit(User user){
        Map<String,String> map = new HashMap<>();
        if (user == null){
            map.put("type","error");
            map.put("msg","数据绑定出错，请联系开发作者!");
            return map;
        }
        if (StringUtils.isEmpty(user.getUsername())){
            map.put("type","error");
            map.put("msg","用户名不能为空!");
            return map;
        }
        if (StringUtils.isEmpty(user.getPassword())){
            map.put("type","error");
            map.put("msg","密码不能为空!");
            return map;
        }
        User exitUser = userService.findByUserName(user.getUsername());
        if (exitUser != null){
            if (!user.getId().equals(exitUser.getId())){
                map.put("type","error");
                map.put("msg","用户已存在!");
                return map;
            }
        }
        if (userService.edit(user) <= 0){
            map.put("type","error");
            map.put("msg","编辑失败!");
            return map;
        }
        map.put("type","success");
        map.put("msg","编辑成功!");
        return map;
    }

    /**
     * 删除用户
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
        if (userService.delete(idsString)<=0){
            map.put("type","error");
            map.put("msg","删除失败!");
            return map;
        }
        map.put("type","success");
        map.put("msg","删除成功!");
        return map;
    }
}
