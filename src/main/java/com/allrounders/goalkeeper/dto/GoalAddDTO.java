package com.allrounders.goalkeeper.dto;

import com.allrounders.goalkeeper.domain.Goal;
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
public class GoalAddDTO {
    private Long goalId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Integer maxPeople;

    @NotNull
    private Integer authCount;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

//    private List<HashtagDTO> hashtagDTOList;
    private String hashtagDTOs;

//    @NotBlank
    private String imgPath;

    public static Goal dtoToEntity(GoalAddDTO goalAddDTO) {

        Goal goal = Goal.builder()
                .title(goalAddDTO.getTitle())
                .content(goalAddDTO.getContent())
                .maxPeople(goalAddDTO.getMaxPeople())
                .authCount(goalAddDTO.getAuthCount())
                .startDate(goalAddDTO.getStartDate())
                .endDate(goalAddDTO.getEndDate())
                .build();

        return goal;
    }

}
