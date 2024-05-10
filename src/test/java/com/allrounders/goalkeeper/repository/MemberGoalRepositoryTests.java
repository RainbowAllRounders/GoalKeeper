package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.domain.MemberGoal;
import lombok.extern.log4j.Log4j2;
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

    // 미션 생성자
    @Test
    public void testInsert() {

        MemberGoal memberGoal = MemberGoal.builder()
                .member(memberRepository.findByMemberId(1L))
                .goal(goalRepository.findByGoalId(1L))
                .role(true)
                .startAlarmDate(goalRepository.findByGoalId(1L).getStartDate())
                .endAlarmDate(goalRepository.findByGoalId(1L).getEndDate())
                .isChecked(false)
                .isSuccess(false)
                .build();

        MemberGoal result = memberGoalRepository.save(memberGoal);
        log.info("memberGoalId: " + result.getMemberGoalId());
    }

    // 미션 참여자
    @Test
    public void testInsert2() {

        MemberGoal memberGoal = MemberGoal.builder()
                .member(memberRepository.findByMemberId(2L))
                .goal(goalRepository.findByGoalId(1L))
                .role(false)
                .startAlarmDate(goalRepository.findByGoalId(1L).getStartDate())
                .endAlarmDate(goalRepository.findByGoalId(1L).getEndDate())
                .isChecked(false)
                .isSuccess(false)
                .build();

        MemberGoal result = memberGoalRepository.save(memberGoal);
        log.info("memberGoalId: " + result.getMemberGoalId());
    }
}
