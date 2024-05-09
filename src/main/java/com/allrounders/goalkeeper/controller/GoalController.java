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
//                          Model model) {
//
//        if(bindingResult.hasErrors()) {
//            log.info("미션 등록 오류");
//            model.addAttribute("fail", "미션 등록 오류");
//            return "redirect:/goal/add";
//        }
//
//        log.info("goalAddDTO: " + goalAddDTO);
//
//        goalService.goalAdd(goalAddDTO);
//        model.addAttribute("success", "미션 등록 성공");
//        return "redirect:/goal/list";
//    }
//
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
//        return "goal/goalList.html";
//    }
}
