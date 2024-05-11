package com.allrounders.goalkeeper.controller;

import com.allrounders.goalkeeper.domain.Member;
import com.allrounders.goalkeeper.dto.member.MyPageInfoDTO;
import com.allrounders.goalkeeper.dto.member.MyPageModifyDTO;
import com.allrounders.goalkeeper.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;

    @GetMapping("/myPage")
    public String myPage(HttpSession httpSession, Model model) {

        // String email = (String) httpSession.getAttribute("email");
        // TODO: 우선 임의로 값 넣음. 추후 session 정보로 불러오는 로직 확인
        String email = "suhyeon1@naver.com";

        // if (email == null) {
        //     System.out.println("Email is not present in the session.");
        //     return "redirect:/login";
        // }

        Member member = memberService.myInfo(email);

        if (member == null) {
            // model.addAttribute("error", "No member found with the provided email.");
            return "member/login";
        }

        MyPageInfoDTO dto = MyPageInfoDTO.fromMember(member);
        model.addAttribute("member", dto);

        return "member/myPage";
    }


    @ResponseBody
    @PutMapping("/updateMyInfo")
    public ResponseEntity<?> updateMyInfo(@RequestBody MyPageModifyDTO modifyDTO, HttpSession session) {
        // String email = (String) session.getAttribute("email");
        // TODO: 우선 임의로 값 넣음. 추후 session 정보로 불러오는 로직 확인
        String email = "suhyeon1@naver.com";

        return memberService.updateMyInfo(email, modifyDTO);
    }

}
