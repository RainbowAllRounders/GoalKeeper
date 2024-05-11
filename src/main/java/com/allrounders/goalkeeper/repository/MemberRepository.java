package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);

//    @Query("SELECT m.memberId FROM Member m WHERE m.email = :email")
//    Long findIdByEmail(String email);

    @Query("SELECT m, ROW_NUMBER() OVER (ORDER BY m.rankPoint DESC, m.nickname ASC) AS rank_row_number FROM Member m")
    List<Member> findAllOrderedByRankPoints();


    //DB에 Email값이 존재하는지 확인
    boolean existsByEmail(String email);
//    //DB에 Nickname값이 존재하는지 확인
//    boolean existsByNickname(String nickname);

    Member findByMemberId(Long memberId);
}
