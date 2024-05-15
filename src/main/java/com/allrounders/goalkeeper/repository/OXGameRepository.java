package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.domain.OXGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OXGameRepository  extends JpaRepository<OXGame, Long>, OXGameCustomRepository {
}
