package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.dto.member.RankDTO;

import java.util.List;

public interface RankRepositoryCustom {
    List<RankDTO> findTop3Rankers();
}
