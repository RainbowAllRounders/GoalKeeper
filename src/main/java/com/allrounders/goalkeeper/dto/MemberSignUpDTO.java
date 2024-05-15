package com.allrounders.goalkeeper.dto;

import com.allrounders.goalkeeper.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberSignUpDTO {


    @NotBlank(message = "이메일 주소를 입력해주세요.")
    @Email(message = "올바른 이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    private int ranking = 999; // Add this line with default value

    private String imgPath = "Flame.svg";

    private Integer rankPoint = 0;

    public Member toEntity() {
        return Member.builder()
                .email(this.getEmail())
                .password(this.getPassword())
                .nickname(this.getNickname())
                .ranking(this.getRanking())
                .imgPath(this.getImgPath())
                .rankPoint(this.getRankPoint())
                .build();
    }
}
