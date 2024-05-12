package com.allrounders.goalkeeper.service;

import com.allrounders.goalkeeper.domain.Goal;
import com.allrounders.goalkeeper.domain.Member;
import com.allrounders.goalkeeper.domain.MemberGoal;
import com.allrounders.goalkeeper.repository.GoalRepository;
import com.allrounders.goalkeeper.repository.MemberGoalRepository;
import com.allrounders.goalkeeper.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberGoalService {

    private final MemberGoalRepository memberGoalRepository;
    private final MemberRepository memberRepository;
    private final GoalRepository goalRepository;

    /**
     * 등록된 미션 참가하기
     */
    public boolean joinGoal(Long memberId, Long goalId) {
        Member member = existMember(memberId);
        Goal goal = existGoal(goalId);

        memberGoalRepository.exist(member.getMemberId(), goal.getGoalId())
                .ifPresentOrElse(
                        memberGoal ->
                        {
                            minusPeople(goal);
                            addPoint(member);
                            memberGoalRepository.deleteById(memberGoal.getMemberGoalId());
                        },

                        () -> {
                            if(goal.getMaxPeople() == goal.getCurPeople())
                                throw new IllegalArgumentException("참여할 수 없습니다!(모집마감)");
                            else {
                                addCurPeople(goal);
                                minusPoint(member);
                                memberGoalRepository.save(MemberGoal.joinGoal(member, goal));
                            }
                        }
                );

        return memberGoalRepository.isJoin(member.getMemberId(), goal.getGoalId());
    }

    /**
     * 참가인원 추가
     */
    private void addCurPeople(Goal goal) {
        goal.addCurPeople();
        goalRepository.save(goal);
    }

    /**
     * 참가인원 차감 
     */
    private void minusPeople(Goal goal) {
        goal.minusCurPeople();
        goalRepository.save(goal);
    }

    /**
     * 참가 취소시 포인트 반환
     */
    private void addPoint(Member member) {
        member.addPoint();
        memberRepository.save(member);
    }

    /**
     * 참여시 포인트 차감
     */
    private void minusPoint(Member member) {
        member.minusPoint();
        memberRepository.save(member);
    }

    /**
     * 존재하는 미션인지 확인
     */
    private Goal existGoal(Long goalId) {

        return goalRepository.findById(goalId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 미션입니다.")
        );

    }

    /**
     * 존재하는 회원인지 확인
     */
    private Member existMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원입니다.")
        );
    }
}
