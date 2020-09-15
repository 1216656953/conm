package com.scmc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller("/main")
public class LoginController {

    @PostMapping("/login")
    @ResponseBody
    public String login(String username, String password) {

        return "localhost:8089";
    }

    @PostMapping("/toLogin")
    @ResponseBody
    public String loginPage() {
        System.out.print("姓名");
        return "";
    }

    @PostMapping("/upload")
    @ResponseBody
    public void uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("sign") String sign) {
        System.out.print(file.getName());
    }
}
