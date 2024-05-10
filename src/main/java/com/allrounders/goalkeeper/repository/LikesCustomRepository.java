package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.domain.Likes;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesCustomRepository {

    Optional<Likes> exist(Long memberId, Long goalId);

    int getGoalLikeCount(Long goalId);

    List<Likes> findByGoalId(Long goalId);
}
