package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.domain.Goal;
import com.allrounders.goalkeeper.dto.GoalListDTO;
import com.allrounders.goalkeeper.dto.MyGoalListDTO;
import com.allrounders.goalkeeper.dto.Top3GoalDTO;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GoalCustomRepository {

    void applyPagination(Pageable pageable, JPAQuery<Goal> query);

    Page<GoalListDTO> listAll(String[] types, String keyword, Pageable pageable);

    Page<MyGoalListDTO> myListAll(Long memberId, String[] types, String keyword, Pageable pageable);

    List<Top3GoalDTO> searchTop3Goal();
}
