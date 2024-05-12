package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.domain.MemberGoal;

import java.util.Optional;

public interface MemberGoalCustomRepository {

    Optional<MemberGoal> exist(Long memberId, Long goalId);

    Boolean isJoin(Long memberId, Long goalId);

    int joinedPeople(Long goalId);

    String findByMemberNickName_goalId(Long goalId);
}
