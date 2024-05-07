package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.domain.Goal;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.allrounders.goalkeeper.domain.QGoal.goal;

@Repository
@AllArgsConstructor
public class GoalCustomRepositoryImpl implements GoalCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Goal> findAllOrderByCreatedDateDesc(Pageable pageable) {

        JPAQuery<Goal> query = jpaQueryFactory.selectFrom(goal)
                .orderBy(goal.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        // query 실행한 결과값
        List<Goal> content = query.fetch();

        // 전체 데이터 개수 파악
        long total = query.fetchCount();

        return new PageImpl<>(content, pageable, total);
    }
}
