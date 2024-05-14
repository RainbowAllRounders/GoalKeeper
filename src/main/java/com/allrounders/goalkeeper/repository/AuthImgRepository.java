package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.domain.AuthImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthImgRepository extends JpaRepository<AuthImg, Long> {

    @Query("SELECT COUNT(ai) FROM AuthImg ai WHERE ai.member.memberId = :memberId AND ai.goal.goalId = :goalId")
    Long countByMemberIdAndGoalId(@Param("memberId") Long memberId, @Param("goalId") Long goalId);
}

