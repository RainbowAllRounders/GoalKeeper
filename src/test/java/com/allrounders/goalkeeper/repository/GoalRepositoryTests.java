package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.domain.Goal;
import com.allrounders.goalkeeper.dto.Top3GoalDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class GoalRepositoryTests {

    @Autowired
    private GoalRepository goalRepository;

    // 미션 목록 추가 테스트
    @Test
    public void testInsert() {

        String startDateString = "2024-05-30";
        String endDateString = "2024-06-08";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startDateString, formatter);
        LocalDate endDate = LocalDate.parse(endDateString, formatter);

        IntStream.rangeClosed(1, 50).forEach(i -> {
            Goal goal = Goal.builder()
                    .title("goal title..." + i)
                    .content("goal content..." + i)
                    .maxPeople(i)
                    .authCount(i+9)
                    .complete("모집 중")
                    .startDate(startDate)
                    .endDate(endDate)
                    .build();

            Goal result = goalRepository.save(goal);
            log.info("goalId: " + result.getGoalId());
        });
    }

    @Autowired
    private GoalRepository goalRepositoryImpl;

    @Test
    public void testTop3Goal() {
        List<Top3GoalDTO> result = goalRepositoryImpl.searchTop3Goal();
        log.info("@@@@@@"+result);

    }

}
