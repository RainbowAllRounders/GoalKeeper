package com.allrounders.goalkeeper.dto.goal;

import com.allrounders.goalkeeper.domain.Goal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoalDetailDTO {

    private String title;
    private String content;
    private int likeCount;
    private int maxPeople;
    private LocalDate startDate;
    private LocalDate endDate;
    private String complete;

    public static GoalDetailDTO fromEntity(Goal goal) {
        return GoalDetailDTO.builder()
                .title(goal.getTitle())
                .content(goal.getContent())
                .likeCount(goal.getLikeCount())
                .maxPeople(goal.getMaxPeople())
                .startDate(goal.getStartDate())
                .endDate(goal.getEndDate())
                .complete(goal.getComplete())
                .build();
    }

}
