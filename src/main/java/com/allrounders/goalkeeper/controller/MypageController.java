package com.allrounders.goalkeeper.controller;

import com.allrounders.goalkeeper.domain.Member;
import com.allrounders.goalkeeper.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

//@RequestMapping("/member")
@Controller
public class MypageController {

    private final MemberService memberService;

    @Autowired
    public MypageController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/myPage")
    public String showMyPage(Model model) {
        // 회원 정보를 모델에 추가
//        model.addAttribute("members", memberService.getMyInfo());
        Member member = memberService.getMyInfo();
        model.addAttribute("member", member);

        // My Page를 보여주는 뷰로 이동
        return "member/myPage"; // HTML 템플릿의 파일 이름
    }
}
