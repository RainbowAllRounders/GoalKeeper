package com.allrounders.goalkeeper.dto;

import com.allrounders.goalkeeper.domain.Hashtag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HashtagDTO {
    private Long tagId;

    @NotNull
    private GoalAddDTO goalAddDTO;

    @NotBlank
    private String tagName;

    public static Hashtag dtoToEntity(HashtagDTO hashtagDTO) {

        Hashtag hashtag = Hashtag.builder()
                .tagId(hashtagDTO.getTagId())
                .tagName(hashtagDTO.getTagName())
                .goal(GoalAddDTO.dtoToEntity(hashtagDTO.getGoalAddDTO()))
                .build();

        return hashtag;
    }
}
