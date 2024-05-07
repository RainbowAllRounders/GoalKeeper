package com.allrounders.goalkeeper.dto;

import com.allrounders.goalkeeper.domain.Goal;
import com.allrounders.goalkeeper.domain.Member;
import com.allrounders.goalkeeper.domain.MemberGoal;
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

    @NotNull
    private Goal goal;

    @NotNull
    private Member member;

    public static MemberGoal dtoToEntity(MemberGoalDTO memberGoalDTO) {
        MemberGoal memberGoal = MemberGoal.builder()
                .goal(memberGoalDTO.goal)
                .member(memberGoalDTO.member)
                .build();

        return memberGoal;
    }
}
