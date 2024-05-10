package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.dto.RankDTO;

import java.util.List;

public interface RankRepositoryCustom {
    List<RankDTO> findTop3Rankers();
}
