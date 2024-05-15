package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.domain.Hashtag;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class HashtagRepositoryTests {

    @Autowired
    private HashtagRepository hashtagRepository;
    @Autowired
    private GoalRepository goalRepository;

    @Test
    public void testInsert() {
        Long goalId = 141L;
        String tagName = "태그";

        IntStream.rangeClosed(1, 3).forEach(i -> {
            Hashtag hashtag = Hashtag.builder()
                    .goal(goalRepository.findByGoalId(goalId))
                    .tagName(tagName + i)
                    .build();

            Hashtag result = hashtagRepository.save(hashtag);
            log.info("hashtagId: " + result.getTagId());
        });
    }
}
