package com.allrounders.goalkeeper.repository.impl;

import com.allrounders.goalkeeper.domain.Goal;
import com.allrounders.goalkeeper.dto.GoalListDTO;
import com.allrounders.goalkeeper.dto.HashtagDTO;
import com.allrounders.goalkeeper.repository.GoalCustomRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.allrounders.goalkeeper.dto.Top3GoalDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
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
import static com.allrounders.goalkeeper.domain.QHashtag.hashtag;
import static com.allrounders.goalkeeper.domain.QMember.member;
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
    
    //좋아요순으로 카드3개
    @Override
    public List<Top3GoalDTO> searchTop3Goal() {
        // 먼저 Goal의 기본 정보와 함께 기본적인 집계 데이터를 가져옵니다.
        List<Top3GoalDTO> goals = jpaQueryFactory
                .select(Projections.fields(Top3GoalDTO.class,
                        goal.goalId,
                        goal.title,
                        goal.authCount,
                        goal.maxPeople,
                        goal.member.size().as("curPeople"),
                        goal.likeCount,
                        goal.complete,
                        goal.startDate,
                        goal.endDate,
                        goal.imgPath
                ))
                .from(goal)
                .orderBy(goal.likeCount.desc())
                .offset(0)
                .limit(3)
                .fetch();

        //방장구하기 -> 나중에 true로 변경해야함 memberGoal.role.eq(true)
        goals.forEach(g -> {
            String roomManager = jpaQueryFactory
                    .select(member.nickname)
                    .from(memberGoal)
                    .join(memberGoal.member, member)
                    .where(memberGoal.role.eq(false).and(memberGoal.goal.goalId.eq(g.getGoalId())))
                    .fetchFirst();
            g.setRoomManager(roomManager);
        });

        // 각 Goal에 대해 연관된 Hashtags를 조회하여 설정합니다.
        goals.forEach(g -> {
            List<HashtagDTO> hashtags = jpaQueryFactory
                    .select(Projections.constructor(HashtagDTO.class, hashtag.tagName))
                    .from(hashtag)
                    .where(hashtag.goal.goalId.eq(g.getGoalId()))
                    .fetch();
            g.setHashtagDTOList(hashtags);
        });

        return goals;
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
