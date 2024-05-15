package com.allrounders.goalkeeper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePointDTO {

    private boolean win;
    private Long memberId;
    private int curPoint; // 변경된 필드명
    private int resultPointWin;
    private int resultPointLose;
}
