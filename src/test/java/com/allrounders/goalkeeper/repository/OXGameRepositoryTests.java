package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.dto.OXGameDTO;
import com.allrounders.goalkeeper.dto.Top3GoalDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class OXGameRepositoryTests {

    @Autowired
    private OXGameRepository oxGameRepository;

    @Test
    public void testRandom5Q() {
        List<OXGameDTO> result = oxGameRepository.findRandom5();
        log.info("@@@@@@"+result);

    }
}
