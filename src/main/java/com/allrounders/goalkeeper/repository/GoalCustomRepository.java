package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.domain.Goal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GoalCustomRepository {
    Page<Goal> findAllOrderByGoalIdDesc(Pageable pageable);
}
