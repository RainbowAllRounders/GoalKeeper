package com.allrounders.goalkeeper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OXGameDTO {
    private Long oxId;

    private String question;

    private String commentary;

    private Boolean isCorrect;
}
