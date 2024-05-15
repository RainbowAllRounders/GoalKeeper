package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.dto.OXGameDTO;

import java.util.List;

public interface OXGameCustomRepository {
    List<OXGameDTO> findRandom5();
}
