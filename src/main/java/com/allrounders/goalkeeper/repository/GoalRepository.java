package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.domain.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, Integer>, GoalCustomRepository {
    Goal findByGoalId(Long goalId);
}
