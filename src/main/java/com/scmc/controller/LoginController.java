package com.scmc.controller;

import com.alibaba.fastjson.JSONObject;
import com.scmc.entity.JsonData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("pub")
public class LoginController {

    @PostMapping("toLogin")
    @ResponseBody
    public String toLogin(@RequestParam("username") String username,@RequestParam("password") String password) {
        try{
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            subject.login(token);
            boolean b = subject.isAuthenticated();
            String id = subject.getSession().getId().toString();
            JsonData jsonData = new JsonData(true, "/authc/index", id,"登录成功");
            return JSONObject.toJSONString(jsonData);
        }catch(Exception e){
            JsonData jsonData = new JsonData(false, "/pub/unauthc",null,"账号密码错误");
            return JSONObject.toJSONString(jsonData);
        }
    }
    @GetMapping("login")
    @ResponseBody
    public String login() {
        //告诉前端，认证失败，需要登录
        JsonData jsonData = new JsonData(false, "/pub/toLogin",null,"需要登录");
        return JSONObject.toJSONString(jsonData);
    }

    @GetMapping("unAuth")
    @ResponseBody
    public String uploadFile() {
        //告诉前端，没有权限
        JsonData jsonData = new JsonData(false,null,null,"访问失败，没有权限");
        return JSONObject.toJSONString(jsonData);
    }
}
