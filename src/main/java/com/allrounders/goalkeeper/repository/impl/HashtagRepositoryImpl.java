package com.allrounders.goalkeeper.repository.impl;

import com.allrounders.goalkeeper.domain.Hashtag;
import com.allrounders.goalkeeper.domain.QHashtag;
import com.allrounders.goalkeeper.repository.HashtagCustomRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.allrounders.goalkeeper.domain.QHashtag.*;

@RequiredArgsConstructor
public class HashtagRepositoryImpl implements HashtagCustomRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Hashtag> findByHashtagList_GoalId(Long goalId) {
        return query.selectFrom(hashtag)
                .where(goalIdEq(goalId))
                .fetch();
    }

    private static BooleanExpression goalIdEq(Long goalId) {
        return hashtag.goal.goalId.eq(goalId);
    }
}
