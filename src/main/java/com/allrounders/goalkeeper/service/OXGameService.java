package com.allrounders.goalkeeper.service;

import com.allrounders.goalkeeper.dto.OXGameDTO;
import com.allrounders.goalkeeper.dto.RankDTO;
import com.allrounders.goalkeeper.repository.OXGameRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OXGameService {

    @Autowired
    public OXGameRepository OXGameRepository;

    public List<OXGameDTO> fimdRandom5Q(){
        return OXGameRepository.findRandom5();
    }
}
