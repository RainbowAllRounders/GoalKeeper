package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Member findByEmail(String email);
  
    //DB에 Email값이 존재하는지 확인
    boolean existsByEmail(String email);
//    //DB에 Nickname값이 존재하는지 확인
//    boolean existsByNickname(String nickname);
}
