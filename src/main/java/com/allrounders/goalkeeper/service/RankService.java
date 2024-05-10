package com.allrounders.goalkeeper.service;

import com.allrounders.goalkeeper.dto.RankDTO;
import com.allrounders.goalkeeper.repository.RankRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@Transactional
public class RankService {

    @Autowired
    public RankRepository rankRepositoryImpl;

    public List<RankDTO> findTop3Ranker(){
        return rankRepositoryImpl.findTop3Rankers();
    }
}
