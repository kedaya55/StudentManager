package com.htao.programmer.controller;

import com.htao.programmer.entity.Student;
import com.htao.programmer.entity.User;
import com.htao.programmer.service.StudentService;
import com.htao.programmer.service.UserService;
import com.htao.programmer.util.CpachaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统主页控制器
 * @Author: kedaya55
 * @Date: 2019-12-11 19:37
 */

@RequestMapping("/system")
@Controller
public class SystemController {

    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;

    @RequestMapping("/index")
    public ModelAndView index(ModelAndView model){
        model.setViewName("system/index");
        return model;
    }

    /**
     * 登陆页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ModelAndView login(ModelAndView model){
        model.setViewName("system/login");
        return  model;
    }

    /**
     * 注销登录
     * @param request
     * @return
     */
    @RequestMapping(value = "/Login_out",method = RequestMethod.GET)
    public String loginOut(HttpServletRequest request){
        request.getSession().setAttribute("user",null);
        return  "redirect:login";
    }

    /**
     * 登陆表单提交
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> login(
            @RequestParam(value = "username",required = true) String username,
            @RequestParam(value = "password",required = true) String password,
            @RequestParam(value="vcode",required=true) String vcode,
            @RequestParam(value="type",required=true) int type,
            HttpServletRequest request
    ){
        Map<String,String> ret = new HashMap<>();

        String loginCpacha = (String)request.getSession().getAttribute("loginCpacha");

        //判断用户名
        if (StringUtils.isEmpty(username)){
            ret.put("type","error");
            ret.put("msg","用户名不能为空!");
            return ret;
        }
        //判断密码
        if (StringUtils.isEmpty(password)){
            ret.put("type","error");
            ret.put("msg","密码不能为空!");
            return ret;
        }

        if(StringUtils.isEmpty(vcode)){
            ret.put("type", "error");
            ret.put("msg", "验证码不能为空!");
            return ret;
        }

        if(!vcode.toUpperCase().equals(loginCpacha.toUpperCase())){
            ret.put("type", "error");
            ret.put("msg", "验证码错误!");
            return ret;
        }

        if (type == 1){
            //管理员
            User user = userService.findByUserName(username);
            if (user == null){
                ret.put("type","error");
                ret.put("msg","用户名不正确!");
                return ret;
            }
            if (!password.equals(user.getPassword())){
                ret.put("type","error");
                ret.put("msg","密码错误!");
                return ret;
            }

            request.getSession().setAttribute("user", user);
        }
        if (type == 2){
            //学生
            Student student = studentService.findByName(username);
            if (student == null){
                ret.put("type","error");
                ret.put("msg","用户名不正确!");
                return ret;
            }
            if (!password.equals(student.getPassword())){
                ret.put("type","error");
                ret.put("msg","密码错误!");
                return ret;
            }

            request.getSession().setAttribute("user", student);
        }

        request.getSession().setAttribute("userType", type);

        ret.put("type","success");
        ret.put("msg","登陆成功!");
        return  ret;
    }

    /**
     * 显示验证码
     * @param request
     * @param vl
     * @param w
     * @param h
     * @param response
     */
    @RequestMapping("/get_cpacha")
    public void getCpacha(HttpServletRequest request,
                          @RequestParam(value = "vl",defaultValue = "4",required = false) Integer vl,
                          @RequestParam(value = "w",defaultValue = "98",required = false) Integer w,
                          @RequestParam(value = "h",defaultValue = "33",required = false) Integer h,
                          HttpServletResponse response){
        CpachaUtil cpachaUtil = new CpachaUtil(vl,w,h);
        String generatorVCode = cpachaUtil.generatorVCode();
        request.getSession().setAttribute("loginCpacha",generatorVCode);
        BufferedImage bufferedImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true);
        try {
            ImageIO.write(bufferedImage,"gif",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
