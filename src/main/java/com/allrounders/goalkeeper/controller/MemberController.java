package com.allrounders.goalkeeper.controller;

import com.allrounders.goalkeeper.dto.member.MemberSignUpDTO;
import com.allrounders.goalkeeper.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private MemberService memberService;


    @GetMapping("/join")
    public String signUp(Model model){
        model.addAttribute("member", new MemberSignUpDTO());
        return "member/join.html";
    }

    @PostMapping("/join")
    public String signUp(MemberSignUpDTO request, RedirectAttributes redirectAttributes){
        memberService.signUp(request);

        return "redirect:/";
    }




}
