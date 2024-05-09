package com.allrounders.goalkeeper.service;

import com.allrounders.goalkeeper.domain.Goal;
import com.allrounders.goalkeeper.dto.GoalAddDTO;
import com.allrounders.goalkeeper.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;

    public void goalAdd(GoalAddDTO goalAddDTO) {
        Goal goal = GoalAddDTO.dtoToEntity(goalAddDTO);
        goalRepository.save(goal);
    }

    public Page<Goal> goalList(Pageable pageable) {
        Page<Goal> goalPage = goalRepository.findAllOrderByGoalIdDesc(pageable);
        return goalPage;
    }
}