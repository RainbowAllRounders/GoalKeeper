package com.allrounders.goalkeeper.repository.impl;

import com.allrounders.goalkeeper.domain.Likes;
import com.allrounders.goalkeeper.repository.LikesCustomRepository;
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
                        .where(likes.member.memberId.eq(memberId),
                                likes.goal.goalId.eq(goalId))
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
                .where(likes.goal.goalId.eq(goalId).and(likes.isLiked.eq(true)))
                .fetchOne().intValue();
    }

    @Override
    public List<Likes> findByGoalId(Long goalId) {
        return query
                .selectFrom(likes)
                .where(likes.goal.goalId.eq(goalId))
                .fetch();
    }
}
