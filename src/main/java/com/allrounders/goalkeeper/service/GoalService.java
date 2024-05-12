package com.allrounders.goalkeeper.service;

import com.allrounders.goalkeeper.domain.Goal;
import com.allrounders.goalkeeper.domain.Hashtag;
import com.allrounders.goalkeeper.domain.Member;
import com.allrounders.goalkeeper.domain.MemberGoal;

import com.allrounders.goalkeeper.dto.GoalAddDTO;
import com.allrounders.goalkeeper.dto.GoalListDTO;
import com.allrounders.goalkeeper.dto.HashtagDTO;
import com.allrounders.goalkeeper.dto.Top3GoalDTO;
import com.allrounders.goalkeeper.dto.goal.GoalDetailDTO;

import com.allrounders.goalkeeper.repository.GoalRepository;
import com.allrounders.goalkeeper.repository.HashtagRepository;
import com.allrounders.goalkeeper.repository.LikesRepository;
import com.allrounders.goalkeeper.repository.MemberGoalRepository;
import com.allrounders.goalkeeper.repository.*;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    private final LikesRepository likesRepository;
    private final MemberGoalRepository memberGoalRepository;
    private final HashtagRepository hashtagRepository;
    private final MemberRepository memberRepository;

    /**
     * 미션 작성
     * @param goalAddDTO
     * @param session
     */
    public void goalAdd(GoalAddDTO goalAddDTO, HttpSession session) {

        Long memberId = (Long)session.getAttribute("memberId");

        Goal goal = GoalAddDTO.dtoToEntity(goalAddDTO);
        Long goalId = goalRepository.save(goal).getGoalId();

        Goal findGoal = validationGoalId(goalId);

        LocalDate startAlarmDate = findGoal.getStartDate();
        LocalDate endAlarmDate = findGoal.getEndDate();

        // 해시태그 저장 ----------------------------------------
        String hashtagDTOs = goalAddDTO.getHashtagDTOs();

        if (hashtagDTOs != null && !hashtagDTOs.isEmpty()) {
            String[] hashtagArray = hashtagDTOs.split("\\s*#\\s*"); // 쉼표로 구분된 해시태그 문자열을 배열로 분할
            for (String tag : hashtagArray) {
                HashtagDTO hashtagDTO = new HashtagDTO();
                hashtagDTO.setGoalId(goalId);
                hashtagDTO.setTagName(tag.trim()); // 태그의 공백을 제거하여 저장
                hashtagRepository.save(HashtagDTO.dtoToEntity(hashtagDTO));
            }
        }

        MemberGoal memberGoal = new MemberGoal(memberId, goalId, true, startAlarmDate, endAlarmDate, false);
        memberGoalRepository.save(memberGoal);

        Member member = memberRepository.findByMemberId(memberId);
        member.updateCurPointAddGoal();
        memberRepository.save(member);
    }

    /**
     * 미션 목록
     * @param pageable
     * @return Page<GoalListDTO>
     */
    public Page<GoalListDTO> goalList(Pageable pageable) {
        return goalRepository.findAllOrderByGoalIdDesc(pageable);
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

        List<Hashtag> findHashtagList = hashtagRepository.findByHashtagList_GoalId(goalId);
        for (Hashtag hashtag : findHashtagList) {
            goal.addHashTag(hashtag);
        }

        memberGoalRepository.joinedPeople(goal.getGoalId());
        String nickName = memberGoalRepository.findByMemberNickName_goalId(goalId);

        return GoalDetailDTO.fromEntity(goalRepository.save(goal), nickName);
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

    public List<Top3GoalDTO> getTop3Goal() {
        List<Top3GoalDTO> Top3Goal = goalRepository.searchTop3Goal();
        return Top3Goal;
    }

}