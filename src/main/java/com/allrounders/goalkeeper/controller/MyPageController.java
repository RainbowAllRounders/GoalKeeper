package com.allrounders.goalkeeper.controller;

import com.allrounders.goalkeeper.domain.Member;
import com.allrounders.goalkeeper.dto.MyGoalProgressDTO;
import com.allrounders.goalkeeper.dto.MyPageInfoDTO;
import com.allrounders.goalkeeper.dto.MyPageModifyDTO;
import com.allrounders.goalkeeper.service.MemberGoalService;
import com.allrounders.goalkeeper.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;
    private final MemberGoalService memberGoalService;

    @GetMapping("/myPage")
    public String myPage(HttpSession session, Model model) {

        Long memberId = (Long) session.getAttribute("member_id");

        if (memberId == null) {
            return "redirect:/member/login";
        }

        Member member = memberService.myInfo(memberId);

        MyPageInfoDTO infoDTO = MyPageInfoDTO.fromMember(member);
        model.addAttribute("member", infoDTO);

        List<MyGoalProgressDTO> progressList = memberGoalService.myGoalProgress(memberId);
        model.addAttribute("goalProgressList", progressList);

        return "/member/myPage";
    }


    @ResponseBody
    @PutMapping("/updateMyInfo")
    public ResponseEntity<?> updateMyInfo(@RequestBody MyPageModifyDTO modifyDTO, HttpSession session) {
        Long memberId = (Long) session.getAttribute("member_id");

        return memberService.updateMyInfo(memberId, modifyDTO);
    }


    @GetMapping("/myGoalList")
    public String goToMyGoalList() {
        return "/member/myGoalList";
    }


    @PostMapping("/verifyPassword")
    public ResponseEntity<?> verifyPassword(@RequestBody Map<String, String> request, HttpSession session) {
        Long memberId = (Long) session.getAttribute("member_id");
        String password = request.get("password");

        boolean isValid = memberService.verifyPassword(memberId, password);

        return ResponseEntity.ok(Map.of("isValid", isValid));
    }

    @DeleteMapping("/unregister")
    @Transactional
    public ResponseEntity<?> unregisterMember(HttpSession session) {
        Long memberId = (Long) session.getAttribute("member_id");

        try {
            memberService.unregister(memberId);
            session.invalidate();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}