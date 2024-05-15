package com.allrounders.goalkeeper.controller;

import com.allrounders.goalkeeper.dto.MemberLoginDTO;
import com.allrounders.goalkeeper.dto.MemberSignUpDTO;
import com.allrounders.goalkeeper.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
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
    public String logout(HttpSession session, HttpServletResponse response) {
        if (session != null) {
            session.removeAttribute("member_id");
            session.removeAttribute("loggedIn");
            session.invalidate(); // 세션 무효화

        }
        // 쿠키 삭제
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        Cookie userCookie = new Cookie("loggedIn", null);
        userCookie.setMaxAge(0);
        userCookie.setPath("/");
        response.addCookie(userCookie);

        return "redirect:/"; // 홈 페이지로 리다이렉트
    }

    @GetMapping("/member/login")
    public String loginPage() {
        return "/member/login.html"; // 로그인 페이지 경로
    }

    @PostMapping("/member/login")
    public String login(HttpServletRequest request, MemberLoginDTO memberLoginDTO, RedirectAttributes redirectAttributes) {
        boolean isLogin = memberService.loginAndSession(memberLoginDTO);
        if (isLogin) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedIn", true); // 로그인 상태를 세션에 저장
            return "redirect:/main";
        } else {
            redirectAttributes.addFlashAttribute("error", "이메일 혹은 비밀번호가 일치하지 않습니다.");
            return "redirect:/member/login";
        }
    }

    @GetMapping("/member/check-login-status")
    public ResponseEntity<Boolean> checkLoginStatus(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("loggedIn") != null);
        return ResponseEntity.ok(isLoggedIn);
    }


}