package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.domain.Hashtag;

import java.util.List;

public interface HashtagCustomRepository {

    List<Hashtag> findByHashtagList_GoalId(Long goalId);

}
