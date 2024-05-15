package com.allrounders.goalkeeper.dto;

import lombok.Data;

@Data
public class UpdatePointDTO {
    private Long memberId;
    private int resultPointWin;
    private int resultPointLose;
}
