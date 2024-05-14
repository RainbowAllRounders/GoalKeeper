package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m.memberId FROM Member m WHERE m.email = :email")
    Long findMemberIdByEmail(@Param("email") String email);

    Member findByMemberId(Long memberId);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    boolean existsByEmailAndPassword(String email, String password);


    @Query("SELECT m, ROW_NUMBER() OVER (ORDER BY m.rankPoint DESC, m.nickname ASC) AS rank_row_number FROM Member m")
    List<Member> findAllOrderedByRankPoints();

}
