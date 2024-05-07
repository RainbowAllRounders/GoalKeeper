package com.allrounders.goalkeeper.dto;

import com.allrounders.goalkeeper.domain.Goal;
import com.allrounders.goalkeeper.domain.Member;
import jakarta.validation.constraints.NotNull;

public class LikesDTO {
    private Integer likeId;

    @NotNull
    private Goal goal;

    @NotNull
    private Member member;

    @NotNull
    private Boolean isLiked;
}
