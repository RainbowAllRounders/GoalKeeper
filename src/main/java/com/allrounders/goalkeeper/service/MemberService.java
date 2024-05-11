package com.allrounders.goalkeeper.service;

import com.allrounders.goalkeeper.domain.Member;
import com.allrounders.goalkeeper.dto.MemberSignUpDTO;
import com.allrounders.goalkeeper.dto.MyPageModifyDTO;
import com.allrounders.goalkeeper.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.hibernate.sql.ast.SqlTreeCreationLogger.LOGGER;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public void signUp(MemberSignUpDTO request) {
        Member member = request.toEntity();
        memberRepository.save(member);
    }

    //    //Repository에서 정의한 메서드 구현
    //    //DB에 존재하면 true, 아닐경우 false를 반환
    //    public boolean existByEmail(String email) {
    //        return memberRepository.existsByEmail(email);
    //    }


    // 회원 정보 조회
    public Member myInfo(String email) {
        return memberRepository.findByEmail(email);
    }

    // 회원 정보 수정
    public ResponseEntity<?> updateMyInfo(String email, MyPageModifyDTO modifyDTO) {
        Member member = memberRepository.findByEmail(email);

        try {
            member.updateMember(modifyDTO.getNickname(), modifyDTO.getPassword());
            memberRepository.save(member);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 매일 자정 ranking 계산
    @Scheduled(cron = "0 0 0 * * *")
    public void updateDailyRank() {
        List<Member> members = memberRepository.findAllOrderedByRankPoints();

        int rank = 1;
        for (Member member : members) {
            member.updateRank(rank++);
            memberRepository.save(member);
        }
    }

    // 탈퇴하기 위한 비밀번호 확인
    public boolean verifyPassword(String email, String password) {
        Member member = memberRepository.findByEmail(email);
//        Long memberId = memberRepository.findIdByEmail(email);
        return member != null && member.getPassword().equals(password);
    }

    // 회원 탈퇴
    @Transactional(readOnly = false)
    public void unregister(String email) {
        System.out.println("@@@@@@@@@@@@@@@1111@@@@@@@@@@@@@@");

            System.out.println("@@@@@@@@@@@@@@@2222@@@@@@@@@@@@@@");

            Member member = memberRepository.findByEmail(email);
            System.out.println("@@@@@@@@@@@@@@@@MemberID:" + member.getMemberId());


                memberRepository.deleteById(member.getMemberId());
                System.out.println("@@@@@@@@@@@@@@@@@33333@@@@@@@@@@@@");

    }

}







