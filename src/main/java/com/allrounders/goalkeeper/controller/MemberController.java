package com.allrounders.goalkeeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    @GetMapping("/hello")
    public String hello() {
        return "main/GoalMain.html";
    }
}
