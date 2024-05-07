package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.domain.MemberGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberGoalRepository extends JpaRepository<MemberGoal, Integer> {

}
