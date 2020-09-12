package com.scmc.controller;

import com.scmc.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller("/main")
public class LoginController {
    @PostMapping("/toLogin")
    @ResponseBody
    public String loginPage(@RequestBody User user){
        System.out.print("姓名"+user);
        return "";
    }
    @PostMapping("/upload")
    @ResponseBody
    public void uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("sign") String sign){
    System.out.print(file.getName());
    }
}
