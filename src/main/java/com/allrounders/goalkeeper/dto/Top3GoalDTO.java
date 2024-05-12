package com.allrounders.goalkeeper.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Top3GoalDTO {

    @NotNull
    private Long goalId;

    @NotBlank
    private String title;

    @NotNull
    private Integer authCount;

    @NotNull
    private Integer maxPeople;

    @NotNull
    private Integer curPeople;

    @NotNull
    private Integer likeCount;

    @NotBlank
    private String complete;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotBlank
    private String imgPath;

    @NotNull
    private String roomManager;

    private List<HashtagDTO> hashtagDTOList;
}
