package com.allrounders.goalkeeper.service;

import com.allrounders.goalkeeper.domain.Member;
import com.allrounders.goalkeeper.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}
