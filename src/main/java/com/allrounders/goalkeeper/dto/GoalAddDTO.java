package com.allrounders.goalkeeper.dto;

import com.allrounders.goalkeeper.domain.Goal;
import com.allrounders.goalkeeper.domain.Hashtag;
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


    private String hashtagDTOs;
  
    private List<HashtagDTO> hashtagDTOList;

//    @NotBlank
    private String imgPath;

    public Goal dtoToEntity() {

        changeList(hashtagDTOs);
        List<Hashtag> hashtagList = this.hashtagDTOList.stream().map(HashtagDTO::dtoToEntity).collect(Collectors.toList());

        Goal goal = Goal.builder()
                .title(this.getTitle())
                .content(this.getContent())
                .maxPeople(this.getMaxPeople())
                .authCount(this.getAuthCount())
                .hashtagList(hashtagList)
                .startDate(this.getStartDate())
                .endDate(this.getEndDate())
                .build();

        return goal;
    }


    public void changeList(String hashtagDTOs) {
        HashtagDTO hashtagDTO = new HashtagDTO();
        String[] split = hashtagDTOs.split("\\s*#\\s*");

        for(String tagName : split) {
            HashtagDTO hash = hashtagDTO.builder().tagName(tagName).build();
            hashtagDTOList.add(hash);
        }
    }
}
