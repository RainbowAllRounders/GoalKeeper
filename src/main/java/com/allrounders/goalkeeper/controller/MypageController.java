package com.allrounders.goalkeeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MypageController {

    @GetMapping("/hello")
    public String hello(){
        return "member/myPage.html";
    }
}
