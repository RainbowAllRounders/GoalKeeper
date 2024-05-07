package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.domain.GoalImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalImgRepository extends JpaRepository<GoalImg, Integer> {
    GoalImg findByGoalImgId(Integer goalImgId);
}
