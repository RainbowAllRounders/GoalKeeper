package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.domain.Goal;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GoalCustomRepository {

    void applyPagination(Pageable pageable, JPAQuery<Goal> query);

    Page<Goal> findAllOrderByGoalIdDesc(Pageable pageable);

//    Page<Goal> searchAll(String[] types, String keyword, Pageable pageable);
}
