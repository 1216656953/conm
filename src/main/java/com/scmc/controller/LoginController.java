package com.scmc.controller;

import com.alibaba.fastjson.JSONObject;
import com.scmc.entity.JsonData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.subject.Subject;
import org.crazycake.shiro.RedisCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.security.krb5.internal.CredentialsUtil;

import java.io.Serializable;

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
            subject.isAuthenticated();
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

    @GetMapping("unauthc")
    @ResponseBody
    public String uploadFile() {
       return "访问失败，没有权限";
    }
}
