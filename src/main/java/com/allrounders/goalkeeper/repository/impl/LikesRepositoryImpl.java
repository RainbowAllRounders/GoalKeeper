package com.allrounders.goalkeeper.repository.impl;

import com.allrounders.goalkeeper.domain.Likes;
import com.allrounders.goalkeeper.repository.LikesCustomRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.allrounders.goalkeeper.domain.QLikes.likes;

@RequiredArgsConstructor
public class LikesRepositoryImpl implements LikesCustomRepository {

    private final JPAQueryFactory query;

    /**
     * 멤버 한 명이 특정 미션에 대한 좋아요 누른지 확인
     */
    @Override
    public Optional<Likes> exist(Long memberId, Long goalId) {

                return Optional.ofNullable(query.selectFrom(likes)
                        .where(memberIdGoalIdEq(memberId, goalId))
                        .fetchFirst());
    }

    /**
     * 좋아요 수 조회
     */
    @Override
    public int getGoalLikeCount(Long goalId) {
        return query
                .select(likes.count())
                .from(likes)
                .where(goalLikesIsTrue(goalId, true))
                .fetchOne().intValue();
    }

    /**
     * 자신의 좋아요 확인
     */
    @Override
    public Boolean findByLikesId_MemberIdAndGoalId(Long memberId, Long goalId) {

        return query.select(likes.isLiked)
                .from(likes)
                .where(memberIdGoalIdEq(memberId, goalId))
                .fetchOne();
    }

    /**
     * 좋아요 테이블에 memberId, goalId 있는지 확인
     */
    private BooleanExpression memberIdGoalIdEq(Long memberId, Long goalId) {
        return memberIdEq(memberId).and(goalIdEq(goalId));
    }

    /**
     * 미션에 좋아요 눌렀는지 확인
     */
    private BooleanExpression goalLikesIsTrue(Long goalId, boolean isLikes) {
        return goalIdEq(goalId).and(likesEq(isLikes));
    }

    private BooleanExpression memberIdEq(Long memberId) {
        return likes.member.memberId.eq(memberId);
    }

    private BooleanExpression goalIdEq(Long goalId) {
        return likes.goal.goalId.eq(goalId);
    }

    private BooleanExpression likesEq(boolean isLikes) {
        return likes.isLiked.eq(isLikes);
    }
}
