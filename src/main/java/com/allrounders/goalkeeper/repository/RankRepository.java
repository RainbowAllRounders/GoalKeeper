package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.domain.Ranks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankRepository extends JpaRepository<Ranks, Integer>, RankRepositoryCustom {
}
