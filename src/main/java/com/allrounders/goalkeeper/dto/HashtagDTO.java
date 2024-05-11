package com.allrounders.goalkeeper.dto;

import com.allrounders.goalkeeper.domain.Hashtag;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HashtagDTO {
    private Long goalId;

    @NotBlank
    private String tagName;

    public static HashtagDTO fromEntity(Hashtag hashtag) {
        return HashtagDTO.builder()
                .tagName(hashtag.getTagName())
                .build();
    }

    public static List<HashtagDTO> fromEntities(List<Hashtag> hashtags) {
        return hashtags.stream()
                .map(HashtagDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public static Hashtag dtoToEntity(HashtagDTO hashtagDTO) {

        Hashtag hashtag = Hashtag.builder()
                .goalId(hashtagDTO.getGoalId())
                .tagName(hashtagDTO.getTagName())
                .build();

        return hashtag;
    }
}
