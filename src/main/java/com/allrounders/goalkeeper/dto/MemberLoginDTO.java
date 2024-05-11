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
public class MemberLoginDTO {


    @NotBlank(message = "이메일 주소를 입력해주세요.")
    @Email(message = "올바른 이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    private int ranking = 0; // Add this line with default value

    public Member toEntity() {
        return Member.builder()
                .email(this.getEmail())
                .password(this.getPassword())
                .ranking(this.getRanking()) // Include ranking field
                .build();
    }
}
