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

        LocalDate startDate = goalRepository.findByGoalId(goalId).getStartDate();
        LocalDate endDate = goalRepository.findByGoalId(goalId).getEndDate();

        MemberGoal memberGoal = new MemberGoal(memberId, goalId, true, startDate, endDate, false);
        memberGoalRepository.save(memberGoal);
    }

    public Page<Goal> goalList(Pageable pageable) {
        Page<Goal> goalPage = goalRepository.findAllOrderByGoalIdDesc(pageable);
        return goalPage;
    }
}