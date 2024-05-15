package com.allrounders.goalkeeper.controller;

import com.allrounders.goalkeeper.service.OXGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final OXGameService oxGameService;

    @GetMapping("/game_main")
    public String gameMain() {
        return "/game/game_main";
    }
    @GetMapping("/game/bat_game")
    public String batGame() {
        return "/game/bat_game";
    }

    @GetMapping("/OXGame")
    public String OXGame(Model model) {
        model.addAttribute("Questions", oxGameService.fimdRandom5Q());
        return "game/OXgame";
    }
}
