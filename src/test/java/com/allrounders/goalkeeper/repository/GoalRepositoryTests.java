package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.domain.Goal;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
                    .imgPath("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.utoimage.com%2F%3Fm%3Dgoods.free%26mode%3Dview%26idx%3D22250682&psig=AOvVaw3uQV0G_bqyBvBPpc1ftysY&ust=1715399628887000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPCa0t6XgoYDFQAAAAAdAAAAABAD")
                    .build();

            Goal result = goalRepository.save(goal);
            log.info("goalId: " + result.getGoalId());
        });
    }

}
