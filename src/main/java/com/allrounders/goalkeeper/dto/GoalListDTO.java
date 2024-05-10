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
public class GoalListDTO {
    @NotNull
    private Long goalId;

    @NotBlank
    private String title;

    @NotNull
    private Integer maxPeople;

//    @NotNull
//    private List<MemberGoalDTO> memberGoalDTOList;

    @NotNull
    private List<LikesDTO> likeDTOList;

    @NotBlank
    private String complete;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private List<HashtagDTO> hashtagDTOList;
}
