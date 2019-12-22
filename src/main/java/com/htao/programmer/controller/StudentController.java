package com.htao.programmer.controller;

import com.htao.programmer.entity.Clazz;
import com.htao.programmer.entity.Student;
import com.htao.programmer.page.Page;
import com.htao.programmer.service.ClazzService;
import com.htao.programmer.service.GradeService;
import com.htao.programmer.service.StudentService;
import com.htao.programmer.util.StringUtil;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: kedaya55
 * @Date: 2019-12-17 18:02
 */
@RequestMapping("/student")
@Controller
public class StudentController {

    @Autowired
    private GradeService gradeService;
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private StudentService studentService;


    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        model.setViewName("student/student_list");
        model.addObject("gradeList",gradeService.findAll());
        model.addObject("clazzList",clazzService.findAll());
        model.addObject("gradeListJson", JSONArray.fromObject(gradeService.findAll()));
        model.addObject("clazzListJson", JSONArray.fromObject(clazzService.findAll()));
        return model;
    }

    @RequestMapping(value = "/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "name",required = false,defaultValue = "") String name,
            HttpServletRequest request,
            Page page
    ){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("username","%"+name+"%");

        //权限管理
        Object userType = request.getSession().getAttribute("userType");
        if ("2".equals(userType.toString())){
            //说明是学生
            Student loginedStudent = (Student) request.getSession().getAttribute("user");
            queryMap.put("username","%"+loginedStudent.getUsername()+"%");
        }

        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());
        map.put("rows", studentService.findList(queryMap));
        map.put("total",studentService.getTotal(queryMap));
        return map;
    }

    /**
     * 添加学生
     * @param student
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Student student){
        Map<String,String> map = new HashMap<>();
        if (student == null){
            map.put("type","error");
            map.put("msg","数据绑定出错，请联系开发作者!");
            return map;
        }
        if (StringUtils.isEmpty(student.getUsername())){
            map.put("type","error");
            map.put("msg","用户名不能为空!");
            return map;
        }
        if (StringUtils.isEmpty(student.getPassword())){
            map.put("type","error");
            map.put("msg","密码不能为空!");
            return map;
        }
        if (student.getClazzId() == null){
            map.put("type","error");
            map.put("msg","请选择所属班级!");
            return map;
        }
        if (isExist(student.getUsername(),null)){
            map.put("type","error");
            map.put("msg","该姓名已存在!");
            return map;
        }

        student.setSn(StringUtil.generateSn("S",""));
        if (studentService.add(student) <= 0){
            map.put("type","error");
            map.put("msg","添加失败!");
            return map;
        }
        map.put("type","success");
        map.put("msg","添加成功!");
        return map;
    }

    /**
     * 编辑学生
     * @param student
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> edit(Student student){
        Map<String,String> map = new HashMap<>();
        if (student == null){
            map.put("type","error");
            map.put("msg","数据绑定出错，请联系开发作者!");
            return map;
        }
        if (StringUtils.isEmpty(student.getUsername())){
            map.put("type","error");
            map.put("msg","用户名不能为空!");
            return map;
        }
        if (StringUtils.isEmpty(student.getPassword())){
            map.put("type","error");
            map.put("msg","密码不能为空!");
            return map;
        }
        if (student.getClazzId() == null){
            map.put("type","error");
            map.put("msg","请选择所属班级!");
            return map;
        }
        if (isExist(student.getUsername(),student.getId())){
            map.put("type","error");
            map.put("msg","该姓名已存在!");
            return map;
        }
        student.setSn(StringUtil.generateSn("S",""));
        if (studentService.edit(student) <= 0){
            map.put("type","error");
            map.put("msg","添加失败!");
            return map;
        }
        map.put("type","success");
        map.put("msg","编辑成功!");
        return map;
    }

    /**
     * 删除学生
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
            if (studentService.delete(idsString)<=0){
                map.put("type","error");
                map.put("msg","删除失败!");
                return map;
            }
        }catch (Exception e){
            map.put("type","error");
            map.put("msg","该学生下存在其他信息，请勿删除!");
            return map;
        }
        map.put("type","success");
        map.put("msg","删除成功!");
        return map;
    }

    /**
     * 上传用户图片
     * @param photo
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/upload_photo",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> uploadPhoto(MultipartFile photo,
            HttpServletRequest request,
            HttpServletResponse response
          ) throws IOException {
        Map<String, String> map = new HashMap<>();
        if (photo == null) {
            map.put("type", "error");
            map.put("msg", "请选择文件!");
            return map;
        }
        int photoMaxSize = 10485760;
        if (photo.getSize()>photoMaxSize){
            map.put("type", "error");
            map.put("msg", "请选择小于10M的图片!");
            return map;
        }
        //获取照片名后缀
        String  suffix = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".")+1,photo.getOriginalFilename().length());
        //规定上传图片类型
        String photoType = "jpg,png,gif,jpeg";
        if (!photoType.contains(suffix.toLowerCase())){
            map.put("type", "error");
            map.put("msg", "文件格式不正确，请上传jpg,png,gif,jpeg图片!");
            return map;
        }
        String savePath = request.getServletContext().getRealPath("/")+"/upload/";
        System.out.println(savePath);
        File saveFilePath = new File(savePath);
        if (!saveFilePath.exists()){
            //如果不存在，就创建一个文件夹upload
            saveFilePath.mkdir();
        }
        String filename = System.currentTimeMillis()+"."+suffix;
        photo.transferTo(new File(savePath+filename));
        map.put("type","success");
        map.put("msg","删除成功!");
        map.put("src",request.getServletContext().getContextPath()+"/upload/"+filename);
        return map;
    }


    public boolean isExist(String username,Long id){
        Student existStudent = studentService.findByName(username);
        if (existStudent != null){
            if (id == null){
                return true;
            }
            if (existStudent.getId().longValue() != id.longValue()){
                return false;
            }
        }
        return false;
    }
}
