package com.allrounders.goalkeeper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinGoalDto {

    private boolean isJoin;
    private boolean role;

    public static JoinGoalDto makeDto(boolean isJoin, boolean role) {
        return JoinGoalDto.builder()
                .isJoin(isJoin)
                .role(role)
                .build();
    }
}
