package com.allrounders.goalkeeper.controller;

import com.allrounders.goalkeeper.domain.Member;
import com.allrounders.goalkeeper.dto.MemberLoginDTO;
import com.allrounders.goalkeeper.dto.MemberSignUpDTO;
import com.allrounders.goalkeeper.repository.MemberRepository;
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
    private final MemberRepository memberRepository;


    @GetMapping("/member/join")
    public String signUp(Model model){
        model.addAttribute("member", new MemberSignUpDTO());
        return "/member/join.html";
    }

    @PostMapping("/member/join")
    public String signUp(MemberSignUpDTO request, RedirectAttributes redirectAttributes){
        memberService.signUp(request);
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
    public String login(MemberLoginDTO memberLoginDTO,HttpSession session, RedirectAttributes redirectAttributes) {
        boolean isLogin = memberService.login(memberLoginDTO);
        String email = memberLoginDTO.getEmail();
        Long memberId = memberService.findMemberIdByEmail(email);

        if (isLogin) {
            session.setAttribute("member_id", memberId);
            return "/main/GoalMain";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid email or password");
            return "/member/login";
        }

    }

//    @PostMapping("/member/login")
//    public String login(MemberLoginDTO memberLoginDTO,HttpSession session, RedirectAttributes redirectAttributes) {
//        boolean isLogin = memberService.login(memberLoginDTO);
//        if (isLogin) {
//            session.setAttribute("member_id", memberLoginDTO.getEmail());
//            System.out.println("========================");
//            System.out.println(session.getAttribute("member_id"));
//            return "/main/GoalMain";
//        } else {
//            redirectAttributes.addFlashAttribute("error", "Invalid email or password");
//            return "redirect:/member/login";
//        }
//    }


//    @PostMapping("/member/login")
//    public @ResponseBody String login(@ModelAttribute MemberDTO memberDTO){
//        boolean isLogin = memberService.login(memberDTO.getEmail(), memberDTO.getPassword());
//        if(isLogin) {
//            return "redirect:/main/GoalMain";
//        }else {
//            return "redirect:/member/login";
//        }
//    }

    @GetMapping("/main/GoalMain")
    public String main(Model model){

        return "/main/GoalMain";
    }

//    @PostMapping("/join")
//    public @ResponseBody String emailCheck(@RequestParam("email") String email){
//        System.out.println("email = " + email);
////        String checkResult = memberService.emailCheck(email);
//        return "체크완료";
//    }

}