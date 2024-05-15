package com.allrounders.goalkeeper.dto;

import com.allrounders.goalkeeper.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageInfoDTO {

    private String email;

    private String password;

    private String nickname;

    private Integer cur_point;

    private Integer rank_point;

    private Integer ranking;

    private String imgPath;


    public static MyPageInfoDTO fromMember(Member member) {
        return MyPageInfoDTO.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .imgPath(member.getImgPath())
                .password(member.getPassword())
                .cur_point(member.getCurPoint())
                .rank_point(member.getRankPoint())
                .ranking(member.getRanking())
                .build();
    }

}
