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

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // 멤버 엔티티의 updateWithDTO 메서드를 호출하여 업데이트 수행
        member.updateWithDTO(updatePointDTO);

        memberRepository.save(member);
    }
}
