package com.allrounders.goalkeeper.controller;

import com.allrounders.goalkeeper.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LikesController {

    private final LikeService likeService;

    /**
     * 좋아요 클릭
     */
    @PostMapping("/like/{goalId}/{memberId}")
    public String addCountLike(@PathVariable Long memberId, @PathVariable Long goalId, Model model) {
        likeService.addCountLike(memberId, goalId);

        return "redirect:/";
    }

}
