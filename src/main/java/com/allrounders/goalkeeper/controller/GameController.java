package com.allrounders.goalkeeper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    @GetMapping("/game_main")
    public String gameMain() {
        return "/game/game_main";
    }
    @GetMapping("/game/bat_game")
    public String batGame() {
        return "/game/bat_game";
    }
}
