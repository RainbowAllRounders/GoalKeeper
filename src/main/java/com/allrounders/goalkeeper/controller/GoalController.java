package com.allrounders.goalkeeper.controller;

import com.allrounders.goalkeeper.dto.GoalAddDTO;
import com.allrounders.goalkeeper.service.GoalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goal")
@Log4j2
@RequiredArgsConstructor
public class GoalController {
    private final GoalService goalService;

    @GetMapping("/add")
    public String addGoalGet(Model model) {
        model.addAttribute("goalAddDTO", new GoalAddDTO());
        return "goal/goalAdd";
    }

    @PostMapping("/add")
    public String addGoal(@ModelAttribute GoalAddDTO goalAddDTO,
                          BindingResult bindingResult,
                          Model model) {

        if(bindingResult.hasErrors()) {
            log.info("미션 등록 오류");
            model.addAttribute("fail", "미션 등록 오류");
            return "goal/goalAdd";
        }

        try {
            goalService.goalAdd(goalAddDTO);
            log.info("미션 등록 성공");
            model.addAttribute("success", "미션 등록 성공");
            return "redirect:/goal/list";

        } catch (Exception e) {
            log.error("미션 등록 실패" + e.getMessage());
            model.addAttribute("fail", "미션 등록 실패");
            return "/goal/goalAdd";
        }
    }

//    @GetMapping("/list")
//    public String goalList(Pageable pageable,
//                           BindingResult bindingResult,
//                           Model model) {
//
//        if(bindingResult.hasErrors()) {
//            log.info("미션 목록 조회 오류");
//            model.addAttribute("fail", "미션 목록 조회 오류");
//            return "redirect:/";
//        }
//        Page<Goal> goalList = goalService.goalList(pageable);
//        model.addAttribute("goalList", goalList);
//        return "goal/goalList";
//    }
}
