package com.allrounders.goalkeeper.dto;

import com.allrounders.goalkeeper.domain.Member;
import lombok.Getter;

@Getter
public class MemberSignUpDTO {


    private String email;

    private String password;

    private String nickname;

    public Member toEntity(){

        return Member.builder()
                .email(this.getEmail())
                .password(this.getPassword())
                .nickname(this.getNickname())
                .build();



    }
}
