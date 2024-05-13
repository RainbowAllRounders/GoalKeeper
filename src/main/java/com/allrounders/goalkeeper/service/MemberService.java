package com.allrounders.goalkeeper.service;

import com.allrounders.goalkeeper.domain.Member;
import com.allrounders.goalkeeper.dto.MemberLoginDTO;
import com.allrounders.goalkeeper.dto.MemberSignUpDTO;
import com.allrounders.goalkeeper.dto.MyPageModifyDTO;
import com.allrounders.goalkeeper.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public void signUp(MemberSignUpDTO request) {
        Member member = request.toEntity();
        memberRepository.save(member);
    }
    //로그인 메서드(예비)
//    public boolean login(MemberLoginDTO loginDTO) {
//        Member member = memberRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
//        return member != null;
//    }
    public boolean login(MemberLoginDTO memberLoginDTO) {
        String email = memberLoginDTO.getEmail();
        String password = memberLoginDTO.getPassword();
        return memberRepository.existsByEmailAndPassword(email, password);
    }


    // email로 memberId 조회
    public Long findMemberIdByEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        return member.getMemberId();
    }

    // 회원 정보 조회
    public Member myInfo(Long memberId) {
        return memberRepository.findById(memberId).orElse(null);
    }


    // 회원 정보 수정
    public ResponseEntity<?> updateMyInfo(Long memberId, MyPageModifyDTO modifyDTO) {
        Member member = memberRepository.findById(memberId).get();

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
    public boolean verifyPassword(Long memberId, String password) {
        Member member = memberRepository.findById(memberId).get();
        return member != null && member.getPassword().equals(password);
    }

    // 회원 탈퇴
    @Transactional(readOnly = false)
    public void unregister(Long memberId) {
        Member member = memberRepository.findById(memberId).get();

        memberRepository.deleteById(member.getMemberId());
    }

}







