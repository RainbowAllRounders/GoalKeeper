package com.allrounders.goalkeeper.dto;

import com.allrounders.goalkeeper.domain.Goal;
import com.allrounders.goalkeeper.domain.Hashtag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private Goal goal;

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

    public Hashtag dtoToEntity() {

        Hashtag hashtag = Hashtag.builder()
                .tagName(this.getTagName())
                .build();

        return hashtag;
    }
}
