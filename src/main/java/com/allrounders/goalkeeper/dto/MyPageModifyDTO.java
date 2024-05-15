package com.allrounders.goalkeeper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageModifyDTO {

    private String nickname;

    private String password;

    private String imgPath;
}
