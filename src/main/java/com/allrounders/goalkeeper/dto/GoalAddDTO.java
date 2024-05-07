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
public class GoalAddDTO {
    private Integer goalId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Integer maxPeople;

    @NotNull
    private List<MemberGoalDTO> memberGoalDTOList;

    @NotNull
    private List<LikesDTO> likeDTOList;

    @NotBlank
    private String complete;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private LocalDate createDate;
}
