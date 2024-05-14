package com.allrounders.goalkeeper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikesResDto {

    private boolean isLiked;

    private int likeCount;

    public static LikesResDto makeDto(boolean isLiked, int likeCount) {
        return LikesResDto.builder()
                .isLiked(isLiked)
                .likeCount(likeCount)
                .build();
    }
}
