package com.allrounders.goalkeeper.service;

import com.allrounders.goalkeeper.domain.Member;
import com.allrounders.goalkeeper.dto.MemberSignUpDTO;
import com.allrounders.goalkeeper.dto.MyPageModifyDTO;
import com.allrounders.goalkeeper.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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




}


