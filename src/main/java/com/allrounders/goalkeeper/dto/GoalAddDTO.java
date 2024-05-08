package com.allrounders.goalkeeper.dto;

import com.allrounders.goalkeeper.domain.Goal;
import com.allrounders.goalkeeper.domain.Likes;
import com.allrounders.goalkeeper.domain.MemberGoal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoalAddDTO {

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

    public static Goal dtoToEntity(GoalAddDTO goalAddDTO) {

        List<MemberGoal> memberGoalList = goalAddDTO.getMemberGoalDTOList().stream()
                .map(MemberGoalDTO::dtoToEntity)
                .collect(Collectors.toList());

        List<Likes> likeList = goalAddDTO.getLikeDTOList().stream()
                .map(LikesDTO::dtoToEntity)
                .collect(Collectors.toList());

        Goal goal = Goal.builder()
                .title(goalAddDTO.getTitle())
                .content(goalAddDTO.getContent())
                .maxPeople(goalAddDTO.getMaxPeople())
                .memberGoalList(memberGoalList)
                .likeList(likeList)
                .complete(goalAddDTO.getComplete())
                .startDate(goalAddDTO.getStartDate())
                .endDate(goalAddDTO.getEndDate())
                .createDate(goalAddDTO.getCreateDate())
                .build();

        return goal;
    }

}
