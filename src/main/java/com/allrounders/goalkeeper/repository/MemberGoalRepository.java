package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.domain.MemberGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberGoalRepository extends JpaRepository<MemberGoal, Long>, MemberGoalCustomRepository {

    List<MemberGoal> findByMember_MemberId(Long memberId);

}
