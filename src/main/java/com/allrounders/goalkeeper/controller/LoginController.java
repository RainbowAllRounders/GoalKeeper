package com.allrounders.goalkeeper.controller;

import com.allrounders.goalkeeper.dto.member.LoginDTO;
import com.allrounders.goalkeeper.dto.member.MemberDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginController {

    @Autowired
    private HttpSession httpSession;

    // GET 요청을 처리하는 핸들러 메서드 추가
    @GetMapping("/login")
    public String showLoginPage() {
        // 로그인 페이지로 이동하거나 다른 처리를 수행할 수 있습니다.
        return "member/login.html"; // 로그인 페이지로 이동하도록 설정
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        // 로그인 처리 후 MemberDTO를 생성
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setEmail("suhyeon1@naver.com"); // 예시로 ID 설정
        memberDTO.setPassword(loginDTO.getPassword());

        // HttpSession에 MemberDTO 속성으로 저장
        httpSession.setAttribute("member", memberDTO);

        // 로그인 후 페이지로 리다이렉트
        return "redirect:/myPage"; // 로그인 후 이동할 페이지 URL
    }
}
