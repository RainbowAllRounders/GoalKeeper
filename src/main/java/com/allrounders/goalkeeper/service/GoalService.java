package com.allrounders.goalkeeper.service;

import com.allrounders.goalkeeper.domain.*;
import com.allrounders.goalkeeper.dto.*;
import com.allrounders.goalkeeper.dto.goal.GoalDetailDTO;
import com.allrounders.goalkeeper.repository.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoalService {

    private final ModelMapper modelMapper;
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
    @Transactional(readOnly = false)
    public void goalAdd(GoalAddDTO goalAddDTO, HttpSession session) {

        Long memberId = (Long)session.getAttribute("member_id");

        // Goal에 생성한 미션 저장 ----------------------------------------
        Goal goal = new Goal();
        goal.addImg(goalAddDTO);    // 이미지 경로 저장
        goalRepository.save(goalAddDTO.dtoToEntity());

        // Hashtag에 해시태그들 저장 ----------------------------------------
        List<Hashtag> hashtagList = goal.getHashtagList();
        hashtagList.forEach(hashtag -> hashtag.addGoal(goal));
        hashtagRepository.saveAll(hashtagList);

        // 미션 생성한 사람의 포인트 차감 ----------------------------------------
        Member member = memberRepository.findByMemberId(memberId);
        member.updateCurPointAddGoal();
        Member savedMember = memberRepository.save(member);

        LocalDate startAlarmDate = goal.getStartDate();
        LocalDate endAlarmDate = goal.getEndDate();

        // MemberGoal에 미션 생성한 사람 저장 ----------------------------------------
        MemberGoal memberGoal = MemberGoal.test(savedMember, goal, true, startAlarmDate, endAlarmDate, false);
        memberGoalRepository.save(memberGoal);

        // isLiked = false 지정
        likesRepository.save(Likes.insertLike(member, goal));
    }

    /**
     * 해시 태그를 갖고 있는 goalList
     * @param goalId
     * @return
     */
//    public GoalListDTO getGoalWithHashtags(Long goalId) {
//        Goal goal = goalRepository.findById(goalId)
//                .orElseThrow(() -> new RuntimeException("해당 ID의 미션을 찾을 수 없습니다: " + goalId));
//
//        List<Hashtag> hashtags = hashtagRepository.findByGoal(goal);
//        List<HashtagDTO> hashtagDTOList = new ArrayList<>();
//
//        for (Hashtag hashtag : hashtags) {
//            hashtagDTOList.add(HashtagDTO.fromEntity(hashtag));
//        }
//
//        for (HashtagDTO hashtagDTO : hashtagDTOList) {
//            goal.addHashTag(hashtagDTO.dtoToEntity());
//        }
//
//        return GoalListDTO.fromEntity(goal);
//    }

    /**
     * 미션 목록 페이지네이션
     * @param pageRequestDTO
     * @return
     */
    public PageResponseDTO<GoalListDTO> goalList(Long memberId, PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("goalId");

        Page<GoalListDTO> result = goalRepository.listAll(memberId, types, keyword, pageable);

        List<GoalListDTO> dtoList = result.getContent().stream()
                .map(goal -> modelMapper.map(goal, GoalListDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<GoalListDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    /**
     * 내 미션 목록 페이지네이션
     * @param pageRequestDTO
     * @return
     */
    public PageResponseDTO<MyGoalListDTO> myList(Long memberId, PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("goalId");

        Page<MyGoalListDTO> result = goalRepository.myListAll(memberId, types, keyword, pageable);

        List<MyGoalListDTO> dtoList = result.getContent().stream()
                .map(goal -> modelMapper.map(goal, MyGoalListDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<MyGoalListDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    /**
     * -- 미션 상세페이지 조회 --
     * 제목, 본문, 인증 횟수
     * 전체 참가인원, 현재 참가인원
     * 미션 시작일, 미션 종료일, 모집 상태
     */
    @Transactional
    public GoalDetailDTO getGoalDetail(HttpSession session) {

        Member member = validationMemberId(session);
        Long memberId = member.getMemberId();

        Goal goal = validationGoalId(session);
        Long goalId = goal.getGoalId();

        List<Hashtag> findHashtagList = hashtagRepository.findByHashtagList_GoalId(goalId);
        findHashtagList.forEach(hashtag -> hashtag.addGoal(goal));
        goal.addLikeCount(likesRepository.getGoalLikeCount(goalId));
        Boolean isLiked = likesRepository.findByLikesId_MemberIdAndGoalId(memberId, goalId);

        memberGoalRepository.curPeopleByGoalId(goalId);
        Boolean isJoin = memberGoalRepository.isJoin(memberId, goalId);
        String nickName = memberGoalRepository.findByMemberNickName_goalId(goalId);

        return GoalDetailDTO.fromEntity(goalRepository.save(goal), nickName, isLiked, isJoin);
    }

    /**
     * 존재하는 멤버인지 확인
     */
    @Transactional(readOnly = true)
    public Member validationMemberId(HttpSession session) {
        Long memberId = (Long) session.getAttribute("member_id");

        return memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 멤버입니다")
        );
    }

    /**
     * 존재하는 미션인지 확인
     */
    @Transactional(readOnly = true)
    public Goal validationGoalId(HttpSession session) {
        Long goalId = (Long) session.getAttribute("goalId");

        return goalRepository.findById(goalId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 미션입니다.")
            );
    }

    public List<Top3GoalDTO> getTop3Goal() {
        List<Top3GoalDTO> Top3Goal = goalRepository.searchTop3Goal();
        return Top3Goal;
    }

}