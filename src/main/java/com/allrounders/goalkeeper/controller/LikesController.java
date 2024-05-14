package com.allrounders.goalkeeper.controller;

import com.allrounders.goalkeeper.dto.LikesResDto;
import com.allrounders.goalkeeper.service.LikeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LikesController {

    private final LikeService likeService;

    /**
     * 좋아요 클릭
     */
    @GetMapping("/goal/like")
    public ResponseEntity<LikesResDto> activeLike(HttpSession session) {
        LikesResDto likesStatus = likeService.activeLikes(session);

        return ResponseEntity.ok(likesStatus);
    }
}
