package com.allrounders.goalkeeper.repository.impl;

import com.allrounders.goalkeeper.repository.MemberGoalCustomRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.allrounders.goalkeeper.domain.QMemberGoal.memberGoal;

@Repository
@AllArgsConstructor
public class MemberGoalCustomRepositoryImpl implements MemberGoalCustomRepository {

    private final JPAQueryFactory query;


    @Override
    public Integer curPeopleByGoalId(Long goalId) {
        return query.select(memberGoal.count())
                .from(memberGoal)
                .where(goalIdEq(goalId))
                .fetchOne().intValue();
    }

    private BooleanExpression goalIdEq(Long goalId) {
        return memberGoal.goal.goalId.eq(goalId);
    }
}
