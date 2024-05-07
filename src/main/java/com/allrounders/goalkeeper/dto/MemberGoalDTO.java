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
    private GoalAddDTO goalAddDTO;

    @NotNull
    private GoalJoinMemberDTO memberDTO;

    @NotNull
    private Boolean role;

    public static MemberGoal dtoToEntity(MemberGoalDTO memberGoalDTO) {
        Goal goal = Goal.builder()
                .title(memberGoalDTO.getGoalAddDTO().getTitle())
                .content(memberGoalDTO.getGoalAddDTO().getContent())
                .build();

        Member member = Member.builder()
                .memberId(memberGoalDTO.getMemberDTO().getMemberId())
                .nickname(memberGoalDTO.getMemberDTO().getNickname())
                .build();

        MemberGoal memberGoal = MemberGoal.builder()
                .goal(goal)
                .member(member)
                .build();

        return memberGoal;
    }
}
