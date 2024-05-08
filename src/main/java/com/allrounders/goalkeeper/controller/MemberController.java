package com.allrounders.goalkeeper.controller;

import com.allrounders.goalkeeper.dto.MemberSignUpDTO;
import com.allrounders.goalkeeper.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private MemberService memberService;

    @GetMapping("/hello")
    public String hello() {
        return "main/GoalMain.html";
    }


    @GetMapping("/member/join")
    public String signUp(Model model){
        model.addAttribute("member", new MemberSignUpDTO());
        return "member/join.html";
    }

    @PostMapping("/member/join")
    public String signUp(MemberSignUpDTO request, RedirectAttributes redirectAttributes){
        memberService.signUp(request);

        return "redirect:/main/GoalMain.html";
    }

}
