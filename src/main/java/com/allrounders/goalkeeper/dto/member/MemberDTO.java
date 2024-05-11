package com.allrounders.goalkeeper.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {

    private Long member_id;

    private String email;

    private String password;

    private String nickname;

    private Integer cur_point;

}
