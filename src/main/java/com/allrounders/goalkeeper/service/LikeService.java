package com.allrounders.goalkeeper.service;

import com.allrounders.goalkeeper.domain.Goal;
import com.allrounders.goalkeeper.domain.Likes;
import com.allrounders.goalkeeper.domain.Member;
import com.allrounders.goalkeeper.repository.GoalRepository;
import com.allrounders.goalkeeper.repository.LikesRepository;
import com.allrounders.goalkeeper.repository.MemberRepository;
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
    public void addCountLike(Long memberId, Long goalId) {
        Member member = existMember(memberId);
        Goal goal = existGoal(goalId);

        likesRepository.exist(memberId, goalId).ifPresentOrElse(
                likes -> {
                    if (likes.getIsLiked() == true) likes.changeLikeStatus(likes.getIsLiked());
                    else if (likes.getIsLiked() == false) likes.changeLikeStatus(likes.getIsLiked());
                },
                () -> {
                    Likes likes = new Likes(member, goal, true);
                    likesRepository.save(likes);
                });
    }

    /**
     * 존재하는 미션인지 확인
     */
    private Goal existGoal(Long goalId) {

        return goalRepository.findById(goalId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 미션입니다.")
        );

    }

    /**
     * 존재하는 회원인지 확인
     */
    private Member existMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원입니다.")
        );
    }

}
