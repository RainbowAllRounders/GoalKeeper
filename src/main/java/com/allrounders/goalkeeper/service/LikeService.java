package com.allrounders.goalkeeper.service;

import com.allrounders.goalkeeper.domain.Goal;
import com.allrounders.goalkeeper.domain.Member;
import com.allrounders.goalkeeper.dto.LikesResDto;
import com.allrounders.goalkeeper.repository.GoalRepository;
import com.allrounders.goalkeeper.repository.LikesRepository;
import com.allrounders.goalkeeper.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeService {

    private final MemberRepository memberRepository;
    private final GoalRepository goalRepository;
    private final LikesRepository likesRepository;

    /**
     * 좋아요 누른상태에서 또 누르면 isLiked -> false
     * 처음 누르면               isLiked -> true
     */
    @Transactional(readOnly = false)
    public LikesResDto activeLikes(HttpSession session) {
        Member member = existMember(session);
//        Goal goal = existGoal(session);
        Goal goal = goalRepository.findById(1L).get();
        likesRepository.exist(member.getMemberId(), goal.getGoalId()).ifPresent(
                likes -> {
                    if (likes.getIsLiked() == true) likes.changeLikeStatus(likes.getIsLiked());
                    else if (likes.getIsLiked() == false) likes.changeLikeStatus(likes.getIsLiked());
                });
        goal.addLikeCount(likesRepository.getGoalLikeCount(goal.getGoalId()));
        goalRepository.save(goal);
        Boolean likeStatus = likesRepository.findByLikesId_MemberIdAndGoalId(member.getMemberId(), goal.getGoalId());

        return LikesResDto.makeDto(likeStatus, goal.getLikeCount());
    }

    /**
     * 존재하는 미션인지 확인
     */
    private Goal existGoal(HttpSession session) {
        Long goalId = (Long) session.getAttribute("goal_id");
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

}
