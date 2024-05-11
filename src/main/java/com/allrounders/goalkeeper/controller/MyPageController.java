package com.allrounders.goalkeeper.controller;

import com.allrounders.goalkeeper.domain.Member;
import com.allrounders.goalkeeper.dto.MemberUnregisterDTO;
import com.allrounders.goalkeeper.dto.MyPageInfoDTO;
import com.allrounders.goalkeeper.dto.MyPageModifyDTO;
import com.allrounders.goalkeeper.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;


@Controller
//@RequestMapping("/member")
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;

    @GetMapping("/member/myPage")
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
    @PutMapping("/member/updateMyInfo")
    public ResponseEntity<?> updateMyInfo(@RequestBody MyPageModifyDTO modifyDTO, HttpSession session) {
        // String email = (String) session.getAttribute("email");
        // TODO: 우선 임의로 값 넣음. 추후 session 정보로 불러오는 로직 확인
        String email = "suhyeon1@naver.com";

        return memberService.updateMyInfo(email, modifyDTO);
    }

    @GetMapping("/game/game_main")
    public String goToGame() { return "/game/game_main"; }

    @GetMapping("/member/myGoalList")
    public String goToMyGoalList() { return "/member/myGoalList"; }


    @PostMapping("/member/verifyPassword")
    public ResponseEntity<?> verifyPassword(@RequestBody Map<String, String> request) {
        // String meberId = (String) session.getAttribute("memberId");
        String email = "suhyeon1@naver.com";
        String password = request.get("password");
        // TODO: 추후에 email대신 memberId로 확인하도록 변경
        boolean isValid = memberService.verifyPassword(email, password);

        return ResponseEntity.ok(Map.of("isValid", isValid));

    }

    @DeleteMapping("/member/unregister")
    @Transactional
    public ResponseEntity<?> unregisterMember(HttpSession session) {
        // String meberId = (String) session.getAttribute("memberId");
        String email = "suhyeon1@naver.com";

        try {
            memberService.unregister(email);
            // session.invalidate();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/main/GoalMain")
    public String goalMain() {
        return "/main/GoalMain";
    }

}
