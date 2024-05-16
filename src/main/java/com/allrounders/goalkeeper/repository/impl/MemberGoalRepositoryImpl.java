package com.allrounders.goalkeeper.repository.impl;

import com.allrounders.goalkeeper.domain.MemberGoal;
import com.allrounders.goalkeeper.repository.MemberGoalCustomRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.allrounders.goalkeeper.domain.QMemberGoal.*;

@RequiredArgsConstructor
public class MemberGoalRepositoryImpl implements MemberGoalCustomRepository {

    private final JPAQueryFactory query;

    /**
     * 참가하기 or 나가기
     */
    @Override
    public Optional<MemberGoal> exist(Long memberId, Long goalId) {
        return Optional.ofNullable(
                query.selectFrom(memberGoal)
                        .where(memberIdGoalIdEq(memberId, goalId))
                        .fetchFirst());
    }

    /**
     * 참여 여부 확인
     */
    @Override
    public Boolean isJoin(Long memberId, Long goalId) {
        return query.select(memberGoal.memberGoalId).
                from(memberGoal)
                .where(memberIdGoalIdEq(memberId, goalId))
                .fetchOne() != null;
    }

    @Override
    public Boolean getRole(Long memberId, Long goalId) {
        return query.select(memberGoal.role)
                .from(memberGoal)
                .where(memberIdGoalIdEq(memberId, goalId))
                .fetchOne();
    }

    /**
     * 미션 생성자 닉네임 조회
     */
    @Override
    public String findByMemberNickName_goalId(Long goalId) {
        return query.select(memberGoal.member.nickname)
                .from(memberGoal)
                .where(goalIdEqMemberRoleEq(goalId))
                .fetchOne();
    }

    /**
     * 현재 참여 인원
     */
    @Override
    public Integer curPeopleByGoalId(Long goalId) {
        return query.select(memberGoal.count())
                .from(memberGoal)
                .where(goalIdEq(goalId))
                .fetchOne().intValue();
    }

    /**
     * goalId가 인자값으로 들어온 값 중에 역할이 미션 생성자인 경우
     */
    private static BooleanExpression goalIdEqMemberRoleEq(Long goalId) {
        return memberGoal.goal.goalId.eq(goalId).and(memberGoal.role.eq(true));
    }

    /**
     * 좋아요 테이블에 memberId, goalId 있는지 확인
     */
    private BooleanExpression memberIdGoalIdEq(Long memberId, Long goalId) {
        return memberIdEq(memberId).and(goalIdEq(goalId));
    }

    private BooleanExpression memberIdEq(Long memberId) {
        return memberGoal.member.memberId.eq(memberId);
    }

    private BooleanExpression goalIdEq(Long goalId) {
        return memberGoal.goal.goalId.eq(goalId);
    }
}
