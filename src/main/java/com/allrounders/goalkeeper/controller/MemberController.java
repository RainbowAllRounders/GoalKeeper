package com.allrounders.goalkeeper.controller;

import com.allrounders.goalkeeper.dto.MemberLoginDTO;
import com.allrounders.goalkeeper.dto.MemberSignUpDTO;
import com.allrounders.goalkeeper.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Log4j2
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member/join")
    public String signUp(Model model){
        model.addAttribute("member", new MemberSignUpDTO());
        return "/member/join.html";
    }

    @PostMapping("/member/join")
    public String signUp(MemberSignUpDTO request, RedirectAttributes redirectAttributes){
        boolean signUpSuccess = memberService.signUp(request);

        if (!signUpSuccess) {
            redirectAttributes.addFlashAttribute("error", "이미 존재하는 이메일 혹은 닉네임 입니다.");
            return "redirect:/member/join";
        }
        return "redirect:/member/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/member/login")
    public String login() {
        return "/member/login.html"; // 로그인 페이지 경로
    }

    @PostMapping("/member/login")
    public String login(MemberLoginDTO memberLoginDTO, RedirectAttributes redirectAttributes) {
        boolean isLogin = memberService.loginAndSession(memberLoginDTO);
        if (isLogin) {
            return "redirect:/main";
        } else {
            redirectAttributes.addFlashAttribute("error", "이메일 혹은 비밀번호가 일치하지 않습니다.");
            return "redirect:/member/login";
        }
    }

}