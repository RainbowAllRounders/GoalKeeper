package com.allrounders.goalkeeper.dto;

import com.allrounders.goalkeeper.domain.Likes;
import com.allrounders.goalkeeper.domain.MemberGoal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoalDTO {
    private Integer goalId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Integer maxPeople;

    private MemberGoal memberGoalId;

    private Likes likeId;

    @NotNull
    private Integer authCount;

    @NotBlank
    private String complete;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private LocalDate createDate;
}
