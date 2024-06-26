package com.allrounders.goalkeeper.controller;

import com.allrounders.goalkeeper.dto.JoinGoalDto;
import com.allrounders.goalkeeper.service.MemberGoalService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MemberGoalController {

    private final MemberGoalService memberGoalService;

    /**
     * 미션 참여하기
     */
    @GetMapping("/goal/join")
    public ResponseEntity<JoinGoalDto> joinGoal(HttpSession session) {
        JoinGoalDto joinStatus = memberGoalService.joinGoal(session);
        return ResponseEntity.ok(joinStatus);
    }

}
