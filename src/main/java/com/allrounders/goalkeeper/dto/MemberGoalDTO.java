package com.allrounders.goalkeeper.dto;

import com.allrounders.goalkeeper.domain.Goal;
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
public class MemberGoalDTO {
    private Integer membergoalId;

    @NotNull
    private Goal goal;

    @NotNull
    private Member member;
}
