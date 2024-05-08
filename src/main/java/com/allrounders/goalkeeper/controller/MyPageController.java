package com.allrounders.goalkeeper.controller;

import com.allrounders.goalkeeper.domain.Member;
import com.allrounders.goalkeeper.dto.MyPageDTO;
import com.allrounders.goalkeeper.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/member")
@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;

    @GetMapping("/myPage")
    public String myPage(HttpSession httpSession, Model model) {

        // String email = (String) httpSession.getAttribute("email");
        // TODO: 우선 임의로 값 넣음. 추후 session 정보로 불러오는 로직 확인
        String email = "suhyeon1@naver.com";

//        if (email == null) {
//            System.out.println("Email is not present in the session.");
//            return "redirect:/login";
//        }

        Member member = memberService.getMemberByEmail(email);

        if (member == null) {
            // model.addAttribute("error", "No member found with the provided email.");
            return "member/login";
        }

        MyPageDTO dto = MyPageDTO.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .password(member.getPassword())
                .cur_point(member.getCurPoint())
                .build();

        model.addAttribute("member", dto);

        return "member/myPage";
    }



}
