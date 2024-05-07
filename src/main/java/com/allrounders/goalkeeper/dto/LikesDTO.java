package com.allrounders.goalkeeper.dto;

import com.allrounders.goalkeeper.domain.Goal;
import com.allrounders.goalkeeper.domain.Likes;
import com.allrounders.goalkeeper.domain.Member;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikesDTO {

    @NotNull
    private Goal goal;

    @NotNull
    private Member member;

    @NotNull
    private Boolean isLiked;

    public static Likes dtoToEntity(LikesDTO likesDTO) {
        Likes likes = Likes.builder()
                .goal(likesDTO.getGoal())
                .member(likesDTO.getMember())
                .isLiked(likesDTO.getIsLiked())
                .build();

        return likes;
    }

}
