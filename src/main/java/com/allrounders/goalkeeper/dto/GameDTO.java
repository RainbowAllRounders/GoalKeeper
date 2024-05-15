package com.allrounders.goalkeeper.dto;

import lombok.*;

@Getter
@NoArgsConstructor( access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GameDTO {
    private boolean loggedIn;

    private Integer cur_point;

    public GameDTO(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

}
