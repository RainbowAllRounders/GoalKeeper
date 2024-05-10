package com.allrounders.goalkeeper.service;

import com.allrounders.goalkeeper.domain.Goal;
import com.allrounders.goalkeeper.domain.MemberGoal;
import com.allrounders.goalkeeper.dto.GoalAddDTO;
import com.allrounders.goalkeeper.repository.GoalRepository;
import com.allrounders.goalkeeper.repository.MemberGoalRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    private final MemberGoalRepository memberGoalRepository;

    public void goalAdd(GoalAddDTO goalAddDTO, HttpSession session) {

        Long memberId = (Long)session.getAttribute("memberId");

        Goal goal = GoalAddDTO.dtoToEntity(goalAddDTO);
        Long goalId = goalRepository.save(goal).getGoalId();

        Goal findGoal = validationGoalId(goalId);

        LocalDate startAlarmDate = findGoal.getStartDate();
        LocalDate endAlarmDate = findGoal.getEndDate();

        MemberGoal memberGoal = new MemberGoal(memberId, goalId, true, startAlarmDate, endAlarmDate, false);
        memberGoalRepository.save(memberGoal);
    }

    private Goal validationGoalId(Long goalId) {
        return goalRepository.findById(goalId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 미션입니다")
        );
    }

    public Page<Goal> goalList(Pageable pageable) {
        Page<Goal> goalPage = goalRepository.findAllOrderByGoalIdDesc(pageable);
        return goalPage;
    }
}