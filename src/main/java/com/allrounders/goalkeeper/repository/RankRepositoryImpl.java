package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.dto.RankDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;

import static com.allrounders.goalkeeper.domain.QMember.member;

@Repository
@AllArgsConstructor
public class RankRepositoryImpl implements RankRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;

    @Override
    public List<RankDTO> findTop3Rankers() {
        return queryFactory
                .select(Projections.constructor(RankDTO.class,//쿼리결과로 RankDTO객체 생성
                        member.ranking,
                        member.rankPoint,
                        member.nickname,
                        member.imgPath))
                .from(member)//기준테이블
                .orderBy(member.ranking.asc()) // rank_point기분 내림차순
                .offset(0) //0부터
                .limit(3) //상위3개만
                .fetch(); //결과 조회 -> 반환
    }
}
