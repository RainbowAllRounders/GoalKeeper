package com.allrounders.goalkeeper.controller;


import com.allrounders.goalkeeper.service.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final RankService rankService;

    @GetMapping("/Intro")
    public String Intro() {
        return "main/Intro";
    }

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("rankpeople", rankService.findTop3Ranker());
        return "main/GoalMain";
    }
}