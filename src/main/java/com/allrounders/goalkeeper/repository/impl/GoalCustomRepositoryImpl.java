package com.allrounders.goalkeeper.repository.impl;

import com.allrounders.goalkeeper.domain.Goal;
import com.allrounders.goalkeeper.repository.GoalCustomRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.allrounders.goalkeeper.domain.QGoal.goal;
import static com.allrounders.goalkeeper.domain.QHashtag.hashtag;
import static com.allrounders.goalkeeper.domain.QMemberGoal.memberGoal;

@Repository
@AllArgsConstructor
public class GoalCustomRepositoryImpl implements GoalCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public void applyPagination(Pageable pageable, JPAQuery<Goal> query) {
        query.offset(pageable.getOffset())
                .limit(pageable.getPageSize());
    }

    @Override
    public Page<Goal> findAllOrderByGoalIdDesc(Pageable pageable) {

        JPAQuery<Goal> query = jpaQueryFactory.selectFrom(goal)
                .orderBy(goal.goalId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        // query 실행한 결과값
        List<Goal> content = query.fetch();

        // 전체 데이터 개수 파악
        long total = query.fetchCount();

        return new PageImpl<>(content, pageable, total);
    }

//    @Override
//    public Page<Goal> searchAll(String[] types, String keyword, Pageable pageable) {
//
//        JPAQuery<Goal> query = jpaQueryFactory.selectFrom(goal);
//
//        if((types != null && types.length > 0) && keyword != null) {
//
//            BooleanBuilder booleanBuilder = new BooleanBuilder();
//
//            for(String type : types) {
//                switch (type) {
//                    case "title":
//                        booleanBuilder.or(goal.title.contains(keyword));
//                        break;
//                    case "nickname":
//                        // 미션 작성자의 닉네임
//                        booleanBuilder.or(memberGoal.member.nickname.contains(keyword));
//                        break;
//                    case "tagName":
//                        // 미션의 태그 이름
//                        booleanBuilder.or(hashtag.tagName.contains(keyword));
//                        break;
//                }
//            }
//            query.where(booleanBuilder);
//        }
//
//        this.applyPagination(pageable, query);
//
//        List<Goal> list = query.fetch();
//
//        Long count = query.fetchCount();
//
//        return new PageImpl<>(list, pageable, count);
//    }
}
