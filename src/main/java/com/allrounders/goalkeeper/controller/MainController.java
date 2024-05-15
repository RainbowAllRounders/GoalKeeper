package com.allrounders.goalkeeper.controller;


import com.allrounders.goalkeeper.service.GoalService;
import com.allrounders.goalkeeper.service.MemberService;
import com.allrounders.goalkeeper.service.RankService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final RankService rankService;
    private final GoalService goalService;
    private final MemberService memberService;

    @GetMapping("/")
    public String Intro() {
        return "main/Intro";
    }

    @GetMapping("/main")
    public String main(Model model, HttpSession session) {

        Long member_id = (Long) session.getAttribute("member_id");

        if(member_id != null) {
            model.addAttribute("member_id", member_id);
            model.addAttribute("memberProfile", memberService.searchProfile(member_id));
        }
        model.addAttribute("rankpeople", rankService.findTop3Ranker());
        model.addAttribute("Top3Goal", goalService.getTop3Goal());
        return "main/GoalMain";
    }
}
