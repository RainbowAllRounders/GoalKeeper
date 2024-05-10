package com.allrounders.goalkeeper.service;

import com.allrounders.goalkeeper.domain.Goal;
import com.allrounders.goalkeeper.dto.goal.GoalAddDTO;
import com.allrounders.goalkeeper.dto.goal.GoalDetailDTO;
import com.allrounders.goalkeeper.repository.GoalRepository;
import com.allrounders.goalkeeper.repository.LikesRepository;
import com.allrounders.goalkeeper.domain.MemberGoal;
import com.allrounders.goalkeeper.repository.GoalRepository;
import com.allrounders.goalkeeper.repository.MemberGoalRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    private final LikesRepository likesRepository;
    private final MemberGoalRepository memberGoalRepository;

    public void goalAdd(GoalAddDTO goalAddDTO, HttpSession session) {

        Long memberId = (Long)session.getAttribute("memberId");

        Goal goal = GoalAddDTO.dtoToEntity(goalAddDTO);
        Long goalId = goalRepository.save(goal).getGoalId();

        Goal findGoal = validationGoalId(goalId);

        LocalDate startAlarmDate = findGoal.getStartDate();
        LocalDate endAlarmDate = findGoal.getEndDate();

        MemberGoal memberGoal = new MemberGoal(memberId, goalId, true, startAlarmDate, endAlarmDate, false);
        memberGoalRepository.save(memberGoal);
    }

    @Transactional(readOnly = true)
    public Goal validationGoalId(Long goalId) {
        return goalRepository.findById(goalId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 미션입니다")
        );
    }

    public Page<Goal> goalList(Pageable pageable) {
        Page<Goal> goalPage = goalRepository.findAllOrderByGoalIdDesc(pageable);
        return goalPage;
    }

    /**
     * -- 미션 상세페이지 조회 --
     * 제목, 본문, 인증 횟수
     * 전체 참가인원, 현재 참가인원
     * 미션 시작일, 미션 종료일, 모집 상태
     */
    public GoalDetailDTO getGoalDetail(Long goalId) {
        Goal goal = validationGoalId(goalId);
        goal.addLikeCount(likesRepository.getGoalLikeCount(goalId));

        return GoalDetailDTO.fromEntity(goalRepository.save(goal));
    }

    /**
     * 존재하는 미션인지 확인
     */
    @Transactional(readOnly = true)
    public Goal validationGoalId(Long goalId) {

        return goalRepository
                .findById(goalId)
                .orElseThrow(
                        () -> new IllegalArgumentException("존재하지 않는 미션입니다.")
                );
    }

}