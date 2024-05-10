package com.allrounders.goalkeeper.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankDTO {
    private int ranking;
    private int rank_point;
    private String nicName;
    private String imgPath;

}
