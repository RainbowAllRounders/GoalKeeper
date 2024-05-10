package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.domain.MemberGoal;
import groovy.util.logging.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class MemberGoalRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private MemberGoalRepository memberGoalRepository;

    @Test
    public void testInsert() {

        MemberGoal memberGoal = MemberGoal.builder()
                .member(memberRepository.findByMemberId(1L))
                .role(true)
                .startAlarmDate(goalRepository.findByGoalId(1L).getStartDate())
                .endAlarmDate(goalRepository.findByGoalId(1L).getEndDate())
                .isChecked(false)
                .isSuccess(false)
                .build();
    }
}
