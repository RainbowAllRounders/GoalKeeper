package com.allrounders.goalkeeper.controller;

import com.allrounders.goalkeeper.service.GoalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goal")
@Log4j2
@RequiredArgsConstructor
public class GoalController {
    private final GoalService goalService;

    @GetMapping("/add")
    public String addGoalGet() {
        return "goal/goalAdd.html";
    }

//    @PostMapping("/add")
//    public String addGoal(@Valid GoalAddDTO goalAddDTO,
//                          BindingResult bindingResult,
//                          RedirectAttributes redirectAttributes) {
//
//        if(bindingResult.hasErrors()) {
//            log.info("미션 등록 오류");
//            redirectAttributes.addFlashAttribute("errors", bindingResult);
//            return "redirect:/goal/goalAdd.html";
//        }
//
//        log.info("goalAddDTO: " + goalAddDTO);
//
//        Integer goalId = goalService.goalAdd(goalAddDTO);
//        redirectAttributes.addFlashAttribute("result", goalId);
//
//        return "redirect:/goal/goalList.html";
//    }

    @GetMapping("/list")
    public String goalList() {
        return "goal/goalList.html";
    }
}
