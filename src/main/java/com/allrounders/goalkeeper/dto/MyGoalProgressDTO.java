package com.allrounders.goalkeeper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyGoalProgressDTO {

    private String title;
    private int progressPercentage;
    private int width;

    public static MyGoalProgressDTO fromEntity(String title, int percentage, int widthFromPercentage) {
        return MyGoalProgressDTO.builder()
                .title(title)
                .progressPercentage(percentage)
                .width(widthFromPercentage)
                .build();
    }
}