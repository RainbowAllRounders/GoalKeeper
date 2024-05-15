package com.allrounders.goalkeeper.service;

import com.allrounders.goalkeeper.domain.Member;
import com.allrounders.goalkeeper.dto.UpdatePointDTO;
import com.allrounders.goalkeeper.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GameService {
    private final MemberRepository memberRepository;

    @Transactional
    public void updateMemberPoints(UpdatePointDTO updatePointDTO) {
        Long memberId = updatePointDTO.getMemberId();
        int resultPointWin = updatePointDTO.getResultPointWin();
        int resultPointLose = updatePointDTO.getResultPointLose();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // 배팅 포인트 이상일 때 포인트 수정
        if (member.getCurPoint() > 0) {
            member.setCurPoint(member.getCurPoint() + resultPointWin);
        }
        if (member.getCurPoint() > 0) {
            member.setCurPoint(member.getCurPoint() - resultPointLose);
        }
        memberRepository.save(member);
    }
}
