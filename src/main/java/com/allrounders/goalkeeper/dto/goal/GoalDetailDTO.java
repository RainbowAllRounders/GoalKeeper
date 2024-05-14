package com.allrounders.goalkeeper.dto.goal;

import com.allrounders.goalkeeper.domain.Goal;
import com.allrounders.goalkeeper.dto.HashtagDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoalDetailDTO {

    private String goalCreator;
    private String title;
    private String content;
    private int maxPeople;
    private int curPeople;
    private int authCount;
    private List<HashtagDTO> hashtagList;
    private LocalDate startDate;
    private LocalDate endDate;
    private String complete;

    public static GoalDetailDTO fromEntity(Goal goal, String goalCreator) {

        List<HashtagDTO> hashtagList = goal.getHashtagList().stream().map(
                        hashtag -> HashtagDTO.builder()
                                        .tagName(hashtag.getTagName())
                                        .build())
                .collect(Collectors.toList());

        return GoalDetailDTO.builder()
                .goalCreator(goalCreator)
                .title(goal.getTitle())
                .content(goal.getContent())
                .hashtagList(hashtagList)
                .maxPeople(goal.getMaxPeople())
                .curPeople(goal.getCurPeople())
                .authCount(goal.getAuthCount())
                .startDate(goal.getStartDate())
                .endDate(goal.getEndDate())
                .complete(goal.getComplete())
                .build();
    }

}
