package com.allrounders.goalkeeper.service;

import com.allrounders.goalkeeper.domain.Member;
import com.allrounders.goalkeeper.dto.MemberSignUpDTO;
import com.allrounders.goalkeeper.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void signUp(MemberSignUpDTO request) {
        Member member = request.toEntity();
        memberRepository.save(member);
    }

    Member getMemberByEmail(String email) {
        return null;
    }
}


