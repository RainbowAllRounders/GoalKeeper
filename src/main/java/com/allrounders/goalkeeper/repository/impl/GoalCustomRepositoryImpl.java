package com.allrounders.goalkeeper.repository.impl;

import com.allrounders.goalkeeper.domain.Goal;
import com.allrounders.goalkeeper.dto.GoalListDTO;
import com.allrounders.goalkeeper.dto.HashtagDTO;
import com.allrounders.goalkeeper.repository.GoalCustomRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.allrounders.goalkeeper.domain.QGoal.goal;

@Repository
@AllArgsConstructor
public class GoalCustomRepositoryImpl implements GoalCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public void applyPagination(Pageable pageable, JPAQuery<Goal> query) {
        query.offset(pageable.getOffset())
                .limit(pageable.getPageSize());
    }

    @Override
    public Page<GoalListDTO> findAllOrderByGoalIdDesc(Pageable pageable) {
        // 페이지네이션 적용한 쿼리
        QueryResults<Tuple> queryResults = jpaQueryFactory
                .select(goal.goalId, goal.title, goal.maxPeople,
                        goal.curPeople, goal.likeCount, goal.authCount,
                        goal.complete, goal.startDate, goal.endDate,
                        goal.imgPath)
                .from(goal)
                .orderBy(goal.goalId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Tuple> tuples = queryResults.getResults();
        List<GoalListDTO> goalListDTOs = tuples.stream()
                .map(tuple -> {
                    GoalListDTO dto = new GoalListDTO();
                    dto.setGoalId(tuple.get(goal.goalId));
                    dto.setTitle(tuple.get(goal.title));
                    dto.setMaxPeople(tuple.get(goal.maxPeople));
                    dto.setCurPeople(tuple.get(goal.curPeople));
                    dto.setLikeCount(tuple.get(goal.likeCount));
                    dto.setAuthCount(tuple.get(goal.authCount));
                    dto.setComplete(tuple.get(goal.complete));
                    dto.setStartDate(tuple.get(goal.startDate));
                    dto.setEndDate(tuple.get(goal.endDate));
//                    dto.setHashtagDTOList(HashtagDTO.fromEntities(tuple.get(goal.hashtagList)));
                    dto.setImgPath(tuple.get(goal.imgPath));
                    return dto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(goalListDTOs, pageable, queryResults.getTotal());
    }

    // 검색
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
