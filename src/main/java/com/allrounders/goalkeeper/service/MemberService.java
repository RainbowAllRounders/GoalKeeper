package com.allrounders.goalkeeper.service;

import com.allrounders.goalkeeper.domain.Member;
import com.allrounders.goalkeeper.dto.member.MemberSignUpDTO;
import com.allrounders.goalkeeper.dto.member.MyPageModifyDTO;
import com.allrounders.goalkeeper.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


    public Member myInfo(String email) {
        return memberRepository.findByEmail(email);
    }

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

    @Scheduled(cron = "0 0 0 * * *")
    public void updateDailyRank() {
        List<Member> members = memberRepository.findAllOrderedByRankPoints();

        int rank = 1;
        for (Member member : members) {
            member.updateRank(rank++);
            memberRepository.save(member);
        }

    }




}


