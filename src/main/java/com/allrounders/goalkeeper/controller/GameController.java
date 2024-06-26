package com.allrounders.goalkeeper.controller;

import com.allrounders.goalkeeper.dto.OXGamePointDTO;
import com.allrounders.goalkeeper.service.MemberService;
import com.allrounders.goalkeeper.service.OXGameService;
import jakarta.servlet.http.HttpSession;
import com.allrounders.goalkeeper.dto.UpdatePointDTO;
import com.allrounders.goalkeeper.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j2
@Controller
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final OXGameService oxGameService;
    private final MemberService memberService;
    private final GameService gameService;

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
      
    @ResponseBody
    @PostMapping("/update-point")
    public ResponseEntity<String> updatePoint(UpdatePointDTO updatePointDTO) {
        try {
            gameService.updateMemberPoints(updatePointDTO);
            log.info(updatePointDTO);
            return ResponseEntity.ok("포인트 업데이트가 완료됨");
        } catch (Exception e) {
            e.printStackTrace();
            log.info(updatePointDTO);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("포인트 업데이트가 실패함.");
        }
    }
}
