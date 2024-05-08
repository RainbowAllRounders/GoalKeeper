package com.allrounders.goalkeeper.service;

import com.allrounders.goalkeeper.domain.Member;
import com.allrounders.goalkeeper.dto.MemberSignUpDTO;
import com.allrounders.goalkeeper.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void signUp(MemberSignUpDTO request) {
        Member member = request.toEntity();
        memberRepository.save(member);
    }


    public Member myInfo(String email) {
        return memberRepository.findByEmail(email);
    }
}


