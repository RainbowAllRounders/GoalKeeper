package com.allrounders.goalkeeper.service;

import com.allrounders.goalkeeper.domain.Goal;
import com.allrounders.goalkeeper.dto.GoalAddDTO;
import com.allrounders.goalkeeper.repository.GoalImgRepository;
import com.allrounders.goalkeeper.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    private final GoalImgRepository goalImgRepository;

    public Integer goalAdd(GoalAddDTO goalAddDTO) {
        Goal goal = GoalAddDTO.dtoToEntity(goalAddDTO);
        Integer goalId = goalRepository.save(goal).getGoalId();
        return goalId;
    }
}