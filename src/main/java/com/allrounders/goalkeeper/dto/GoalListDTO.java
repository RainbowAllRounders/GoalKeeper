package com.allrounders.goalkeeper.dto;

import com.allrounders.goalkeeper.domain.Goal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @NotNull
    private Integer curPeople;

    @NotNull
    private Integer likeCount;

    @NotNull
    private Integer authCount;

    @NotBlank
    private String complete;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private List<HashtagDTO> hashtagDTOList = new ArrayList<>();

    private String imgPath;

    @NotNull
    private String writer;

    @NotNull
    private Boolean isLiked;

    public static GoalListDTO fromEntity(Goal goal) {
        return GoalListDTO.builder()
                .goalId(goal.getGoalId())
                .title(goal.getTitle())
                .maxPeople(goal.getMaxPeople())
                .curPeople(goal.getCurPeople())
                .likeCount(goal.getLikeCount())
                .authCount(goal.getAuthCount())
                .complete(goal.getComplete())
                .startDate(goal.getStartDate())
                .endDate(goal.getEndDate())
                .hashtagDTOList(HashtagDTO.fromEntities(goal.getHashtagList()))
                .imgPath(goal.getImgPath())
                .build();
    }

}
