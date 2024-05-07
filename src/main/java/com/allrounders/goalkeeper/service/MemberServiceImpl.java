package com.allrounders.goalkeeper.service;

import com.allrounders.goalkeeper.domain.Member;
import com.allrounders.goalkeeper.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final HttpSession session;


    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, HttpSession session) {
        this.memberRepository = memberRepository;
        this.session = session;
    }


    @Override
    public Member getMyInfo() {
        String userEmail = (String) session.getAttribute("myEmail");

        if (userEmail != null) {
            // 이메일을 사용하여 회원 정보를 검색합니다.
            List<Member> memberOptional = memberRepository.findByEmail(userEmail);
            // Optional에서 회원을 가져와 반환합니다. 회원을 찾지 못하면 null이 아닌 Optional.empty()를 반환합니다.
            return (Member) memberOptional;
        } else {
            // 세션에서 이메일을 찾을 수 없는 경우 처리합니다.
            return null; // 또는 요구 사항에 따라 예외를 throw합니다.
        }
    }
}
