package com.allrounders.goalkeeper.service;

import com.allrounders.goalkeeper.domain.Goal;
import com.allrounders.goalkeeper.domain.Member;
import com.allrounders.goalkeeper.domain.MemberGoal;
import com.allrounders.goalkeeper.dto.JoinGoalDto;
import com.allrounders.goalkeeper.dto.MyGoalProgressDTO;
import com.allrounders.goalkeeper.repository.AuthImgRepository;
import com.allrounders.goalkeeper.repository.GoalRepository;
import com.allrounders.goalkeeper.repository.MemberGoalRepository;
import com.allrounders.goalkeeper.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberGoalService {

    private final MemberGoalRepository memberGoalRepository;
    private final MemberRepository memberRepository;
    private final GoalRepository goalRepository;
    private final AuthImgRepository authImgRepository;

    /**
     * 등록된 미션 참가하기
     */
    public JoinGoalDto joinGoal(HttpSession session) {
        Member member = existMember(session);
        Long memberId = member.getMemberId();

        Goal goal = existGoal(session);
        Long goalId = goal.getGoalId();

        memberGoalRepository.exist(memberId, goalId)
                .ifPresentOrElse(
                        memberGoal ->
                        {
                            if (memberGoal.getRole()) return;
                            minusPeople(goal);
                            addPoint(member);
                            memberGoalRepository.deleteById(memberGoal.getMemberGoalId());
                        },

                        () -> {
                            if(goal.getMaxPeople() == goal.getCurPeople())
                                throw new IllegalArgumentException("참여할 수 없습니다!(모집마감)");
                            else {
                                addCurPeople(goal);
                                minusPoint(member);
                                memberGoalRepository.save(MemberGoal.joinGoal(member, goal));
                            }
                        }
                );
        Boolean isJoin = memberGoalRepository.isJoin(memberId, goalId);
        Boolean role = memberGoalRepository.getRole(memberId, goalId);
        return JoinGoalDto.makeDto(isJoin, role);
    }

    /**
     * 참가인원 추가
     */
    private void addCurPeople(Goal goal) {
        goal.addCurPeople();
        goalRepository.save(goal);
    }

    /**
     * 참가인원 차감 
     */
    private void minusPeople(Goal goal) {
        goal.minusCurPeople();
        goalRepository.save(goal);
    }

    /**
     * 참가 취소시 포인트 반환
     */
    private void addPoint(Member member) {
        member.addPoint();
        memberRepository.save(member);
    }

    /**
     * 참여시 포인트 차감
     */
    private void minusPoint(Member member) {
        member.minusPoint();
        memberRepository.save(member);
    }

    /**
     * 존재하는 미션인지 확인
     */
    private Goal existGoal(HttpSession session) {
        Long goalId = (Long) session.getAttribute("goalId");
        return goalRepository.findById(goalId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 미션입니다.")
        );

    }

    /**
     * 존재하는 회원인지 확인
     */
    private Member existMember(HttpSession session) {
        Long memberId = (Long) session.getAttribute("member_id");
        return memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원입니다.")
        );
    }

    /**
     * 인증 횟수를 이용한 진행률 계산
     */
    public List<MyGoalProgressDTO> myGoalProgress(Long memberId) {
        List<MyGoalProgressDTO> progressList = new ArrayList<>();
        List<MemberGoal> memberGoalList = memberGoalRepository.findByMember_MemberId(memberId);

        for (MemberGoal memberGoal : memberGoalList) {
            String title = memberGoal.getGoal().getTitle();
            Long goalId = memberGoal.getGoal().getGoalId();
            Long count = authImgRepository.countByMemberIdAndGoalId(memberId, goalId);
            Integer total = memberGoal.getGoal().getAuthCount();

            int percentage = (int) Math.round((double) count / total * 100);
            int widthFromPercentage = (int) Math.round(172 * ((double) percentage / 100));

            MyGoalProgressDTO progressDTO = MyGoalProgressDTO.fromEntity(title, percentage, widthFromPercentage);
            progressList.add(progressDTO);
        }

        return progressList;
    }

}
