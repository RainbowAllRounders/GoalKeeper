package com.allrounders.goalkeeper.controller;

import com.allrounders.goalkeeper.dto.OXGamePointDTO;
import com.allrounders.goalkeeper.service.MemberService;
import com.allrounders.goalkeeper.service.OXGameService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final OXGameService oxGameService;
    private final MemberService memberService;

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

    @ResponseBody
    @PutMapping("/OXUpdatePoint")
    public void OXUpdatePoint(@RequestBody OXGamePointDTO pointDTO, HttpSession session) {
        Long memberId = (Long) session.getAttribute("member_id");
        memberService.updateOXPoint(memberId, pointDTO);
    }
}
