package com.allrounders.goalkeeper.controller;

import com.allrounders.goalkeeper.service.MemberGoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberGoalController {

    private final MemberGoalService memberGoalService;

    /**
     * 미션 참여하기
     */
    @PostMapping("/join/goal/{goalId}/member/{memberId}")
    public String joinGoal(@PathVariable Long memberId, @PathVariable Long goalId, Model model) {
        model.addAttribute("isJoined",memberGoalService.joinGoal(memberId, goalId));
        return "redirect:/";
    }

}
