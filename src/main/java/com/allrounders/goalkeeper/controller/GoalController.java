package com.allrounders.goalkeeper.controller;

import com.allrounders.goalkeeper.dto.GoalAddDTO;
import com.allrounders.goalkeeper.dto.GoalListDTO;
import com.allrounders.goalkeeper.dto.PageRequestDTO;
import com.allrounders.goalkeeper.dto.PageResponseDTO;
import com.allrounders.goalkeeper.service.GoalService;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "GoalAdd POST", notes = "POST 방식으로 미션 등록")
    @PostMapping("/add")
    public String addGoal(@ModelAttribute GoalAddDTO goalAddDTO,
                          HttpSession session,
                          BindingResult bindingResult,
                          Model model) {

        log.info(goalAddDTO);

        if(bindingResult.hasErrors()) {
            log.info("미션 등록 오류");
            model.addAttribute("fail", "미션 등록 오류");
            return "goal/goalAdd";
        }

        try {
            goalService.goalAdd(goalAddDTO, session);
            log.info("미션 등록 성공");
            model.addAttribute("success", "미션 등록 성공");
            return "redirect:/goal/list";

        } catch (Exception e) {
            log.error("미션 등록 실패" + e.getMessage());
            model.addAttribute("fail", "미션 등록 실패");
            return "/goal/goalAdd";
        }
    }

    /**
     * 페이지네이션으로 goal 목록 최신순으로 8개씩 가져오기
     * @param model
     * @param pageRequestDTO
     * @return
     */
    @GetMapping("/list")
    public String goalList(Model model, PageRequestDTO pageRequestDTO) {

        PageResponseDTO<GoalListDTO> goalList = goalService.goalList(pageRequestDTO);

        model.addAttribute("goalList", goalList);
        return "goal/goalList";
    }

    @GetMapping("/detail/{goalId}")
    public String goalDetailPage(@PathVariable Long goalId, Model model) {

        model.addAttribute("goalDetail", goalService.getGoalDetail(goalId));

        return "";
    }
}
