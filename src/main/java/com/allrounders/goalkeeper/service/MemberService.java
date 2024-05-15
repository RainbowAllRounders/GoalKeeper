package com.allrounders.goalkeeper.service;

import com.allrounders.goalkeeper.domain.Member;
import com.allrounders.goalkeeper.dto.MemberLoginDTO;
import com.allrounders.goalkeeper.dto.MemberSignUpDTO;
import com.allrounders.goalkeeper.dto.MyPageModifyDTO;
import com.allrounders.goalkeeper.dto.OXGamePointDTO;
import com.allrounders.goalkeeper.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public boolean signUp(MemberSignUpDTO request) {
        String email = request.getEmail();
        String nickname = request.getNickname();

        boolean emailExists = memberRepository.existsByEmail(email);
        boolean nicknameExists = memberRepository.existsByNickname(nickname);

        if (emailExists || nicknameExists) {
            return false; // 이메일 또는 닉네임이 이미 존재하면 false 반환
        } else {
            Member member = request.toEntity();
            memberRepository.save(member);
            return true; // 회원 가입 성공 시 true 반환
        }
    }

    public boolean login(MemberLoginDTO memberLoginDTO) {
        String email = memberLoginDTO.getEmail();
        String password = memberLoginDTO.getPassword();
        return memberRepository.existsByEmailAndPassword(email, password);
    }
    public boolean loginAndSession(MemberLoginDTO memberLoginDTO) {
        boolean isLogin = login(memberLoginDTO);
        if (isLogin) {
            Long memberId = memberRepository.findMemberIdByEmail(memberLoginDTO.getEmail());
            // 현재 요청에 대한 세션 가져오기
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true); // true로 해서 세션이 없으면 생성
            session.setAttribute("member_id", memberId);
            return true;
        }
        return false;
    }

    public boolean isLoggedIn() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false); // 세션이 없으면 null 반환

        if (session != null) {
            Long memberId = (Long) session.getAttribute("member_id");
            return memberId != null;
        }
        return false;
    }


    // 존재하는 회원인지 확인
    private Member validationMemberId(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 회원입니다.")
        );
    }

    // 회원 정보 조회
    public Member myInfo(HttpSession session) {
        Long memberId = (Long) session.getAttribute("member_id");

        return validationMemberId(memberId);
    }

    // 회원 정보 수정
    public ResponseEntity<?> updateMyInfo(Long memberId, MyPageModifyDTO modifyDTO) {
        Member member = validationMemberId(memberId);

        try {
            member.updateMember(modifyDTO);
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
        Member member = validationMemberId(memberId);

        return member.getPassword().equals(password);
    }

    // 회원 탈퇴
    @Transactional(readOnly = false)
    public void unregister(Long memberId) {
        Member member = validationMemberId(memberId);

        memberRepository.deleteById(member.getMemberId());
    }

    //프로필이미지 조회
    public String searchProfile(Long memberId) {
        return memberRepository.findImgPathById(memberId);
    }

    //OXGame으로 번 포인트 Update
    public void updateOXPoint(Long memberId, OXGamePointDTO pointDTO) {
        Member member = validationMemberId(memberId);

        try {
            member.updatePointOXGame(pointDTO.getPoint());
            memberRepository.save(member);
        } catch (IllegalStateException e) {
           log.info("포인트적립오류");
        }
    }
}







