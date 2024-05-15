package com.allrounders.goalkeeper.repository.impl;

import com.allrounders.goalkeeper.domain.OXGame;
import com.allrounders.goalkeeper.dto.OXGameDTO;
import com.allrounders.goalkeeper.dto.RankDTO;
import com.allrounders.goalkeeper.repository.OXGameCustomRepository;
import com.allrounders.goalkeeper.repository.RankRepositoryCustom;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.allrounders.goalkeeper.domain.QOXGame.oXGame;

@Repository
@AllArgsConstructor
public class OXGameRepositoryImpl  implements OXGameCustomRepository {

    @Autowired
    private JPAQueryFactory queryFactory;

    @Override
    public List<OXGameDTO> findRandom5() {
        return queryFactory
                .select(Projections.constructor(OXGameDTO.class,//쿼리결과로 OXGameDTO객체 생성
                        oXGame.oxId,
                        oXGame.question,
                        oXGame.commentary,
                        oXGame.isCorrect))
                .from(oXGame)//기준테이블
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .offset(0) //0부터
                .limit(5) //상위5개만
                .fetch(); //결과 조회 -> 반환
    }
}
