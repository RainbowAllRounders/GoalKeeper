package com.allrounders.goalkeeper.repository.impl;

import com.allrounders.goalkeeper.domain.Goal;
import com.allrounders.goalkeeper.dto.GoalListDTO;
import com.allrounders.goalkeeper.dto.HashtagDTO;
import com.allrounders.goalkeeper.dto.MyGoalListDTO;
import com.allrounders.goalkeeper.dto.Top3GoalDTO;
import com.allrounders.goalkeeper.repository.GoalCustomRepository;
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
import static com.allrounders.goalkeeper.domain.QLikes.likes;
import static com.allrounders.goalkeeper.domain.QMember.member;
import static com.allrounders.goalkeeper.domain.QMemberGoal.memberGoal;

@Repository
@AllArgsConstructor
public class GoalCustomRepositoryImpl implements GoalCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 정렬 및 페이지네이션 적용
     * @param pageable
     * @param query
     */
    public void applyPagination(Pageable pageable, JPAQuery<Goal> query) {
        query.orderBy(goal.goalId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
    }

    /**
     * goal에서 select from
     * @return JPAQuery
     */
    private JPAQuery<Goal> selectFromGoal() {
        return jpaQueryFactory
                .select(goal)
                .from(goal);
    }

    /**
     * type 3가지에 따라 where절 추가
     * @param types
     * @param query
     */
    private BooleanBuilder typeFilter(String[] types, JPAQuery<Goal> query) {
        // 각 타입에 대한 조건을 OR 조건으로 묶어서 한 번에 처리
        BooleanBuilder whereClause = new BooleanBuilder();
        if(types != null){
            for (String type : types) {
                if(type != null) {
                    switch (type) {
                        case "r":
                            whereClause.or(goal.complete.eq("모집 중"));
                            break;
                        case "p":
                            whereClause.or(goal.complete.eq("진행 중"));
                            break;
                        case "c":
                            whereClause.or(goal.complete.eq("완료"));
                            break;
                    }
                }
            }
        }
        return whereClause;
    }

    /**
     * 미션 목록 페이지
     * @param types
     * @param keyword
     * @param pageable
     * @return page
     */
    @Override
    public Page<GoalListDTO> listAll(String[] types, String keyword, Pageable pageable) {

        JPAQuery<Goal> query = selectFromGoal();

        query.where(typeFilter(types, query));

        this.applyPagination(pageable, query);

        // 쿼리 결과를 GoalListDTO로 변환
        List<GoalListDTO> goals = query.fetch().stream()
                .map(GoalListDTO::fromEntity)
                .collect(Collectors.toList());

        // 미션 작성자
        goals.forEach(g -> {
            String writer = jpaQueryFactory
                    .select(memberGoal.member.nickname)
                    .from(memberGoal)
                    .where(memberGoal.role.eq(true)
                    .and(memberGoal.goal.goalId.eq(g.getGoalId())))
                    .fetchFirst();
            g.setWriter(writer);

            // 로그인한 member의 좋아요 여부 확인
            Boolean isLiked = jpaQueryFactory
                    .selectFrom(likes)
                    .where(likes.goal.goalId.eq(g.getGoalId()))
                    .fetchFirst() != null;
            g.setIsLiked(isLiked);
        });

        // 각 Goal에 대해 연관된 Hashtags를 조회하여 설정
        goals.forEach(g -> {
            List<HashtagDTO> hashtags = jpaQueryFactory
                    .select(Projections.constructor(HashtagDTO.class, hashtag.tagName))
                    .from(hashtag)
                    .where(hashtag.goal.goalId.eq(g.getGoalId()))
                    .fetch();
            g.setHashtagDTOList(hashtags);
        });

        long count = query.fetchCount();

        return new PageImpl<>(goals, pageable, count);
    }

    /**
     * 내 참여 미션 목록 페이지
     * @param types
     * @param keyword
     * @param pageable
     * @return page
     */
    @Override
    public Page<MyGoalListDTO> myListAll(Long memberId, String[] types, String keyword, Pageable pageable) {

        JPAQuery<Goal> query = selectFromGoal();

        query.innerJoin(memberGoal).on(memberGoal.goal.eq(goal))
                .where(typeFilter(types, query)
                .and(memberGoal.member.memberId.eq(memberId)));

        this.applyPagination(pageable, query);

        // 쿼리 결과를 MyGoalListDTO로 변환
        List<MyGoalListDTO> goals = query.fetch().stream()
                .map(MyGoalListDTO::fromEntity)
                .collect(Collectors.toList());

        // 미션 작성자
        goals.forEach(g -> {
            String writer = jpaQueryFactory
                    .select(memberGoal.member.nickname)
                    .from(memberGoal)
                    .where(memberGoal.role.eq(true)
                            .and(memberGoal.goal.goalId.eq(g.getGoalId())))
                    .fetchFirst();
            g.setWriter(writer);

            // 로그인한 member의 좋아요 여부 확인
            Boolean isLiked = jpaQueryFactory
                    .selectFrom(likes)
                    .where(likes.goal.goalId.eq(g.getGoalId()))
                    .fetchFirst() != null;
            g.setIsLiked(isLiked);
        });

        // 각 Goal에 대해 연관된 Hashtags를 조회하여 설정
        goals.forEach(g -> {
            List<HashtagDTO> hashtags = jpaQueryFactory
                    .select(Projections.constructor(HashtagDTO.class, hashtag.tagName))
                    .from(hashtag)
                    .where(hashtag.goal.goalId.eq(g.getGoalId()))
                    .fetch();
            g.setHashtagDTOList(hashtags);
        });

        // 미션마다 나의 성공 여부
        goals.forEach(g -> {
            Boolean isSuccess = jpaQueryFactory
                    .select(memberGoal.isSuccess)
                    .from(memberGoal)
                    .where(memberGoal.goal.goalId.eq(g.getGoalId())
                            .and(memberGoal.member.memberId.eq(memberId)))
                    .fetchOne();
            g.setIsSuccess(isSuccess);
        });

        long count = query.fetchCount();

        return new PageImpl<>(goals, pageable, count);
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
                        goal.curPeople,
                        goal.likeCount,
                        goal.complete,
                        goal.startDate,
                        goal.endDate
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
                    .where(memberGoal.role.eq(true).and(memberGoal.goal.goalId.eq(g.getGoalId())))
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
}
