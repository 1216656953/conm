package com.scmc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("authc")
public class IndexController {
    @PostMapping("success")
    @ResponseBody
    public String success() {
        return "登录成功";
    }
    @PostMapping("index")
    @ResponseBody
    public String homepage() {
        return "主页";
    }
}