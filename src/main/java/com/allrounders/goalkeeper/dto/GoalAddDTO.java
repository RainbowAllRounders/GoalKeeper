package com.allrounders.goalkeeper.dto;

import com.allrounders.goalkeeper.domain.Goal;
import com.allrounders.goalkeeper.domain.Hashtag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoalAddDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Integer maxPeople;

    @NotNull
    private Integer authCount;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

//    private List<HashtagDTO> hashtagDTOList;
    private String hashtagDTOs;

//    @NotBlank
//    private String imgPath;

    public List<Hashtag> parseHashtags() {
        List<Hashtag> hashtagList = new ArrayList<>();
        if (hashtagDTOs != null && !hashtagDTOs.isEmpty()) {
            String[] hashtagArray = hashtagDTOs.split("\\s*#\\s*"); // 쉼표로 구분된 해시태그 문자열을 배열로 분할
            for (String tag : hashtagArray) {
                Hashtag hashtag = new Hashtag();
                hashtag.setTagName(tag.trim()); // 태그의 공백을 제거하여 저장
                hashtagList.add(hashtag);
            }
        }
        return hashtagList;
    }

    public static Goal dtoToEntity(GoalAddDTO goalAddDTO) {

        List<Hashtag> hashtagList = goalAddDTO.parseHashtags();

        Goal goal = Goal.builder()
                .title(goalAddDTO.getTitle())
                .content(goalAddDTO.getContent())
                .maxPeople(goalAddDTO.getMaxPeople())
                .authCount(goalAddDTO.getAuthCount())
                .startDate(goalAddDTO.getStartDate())
                .endDate(goalAddDTO.getEndDate())
                .hashtagList(hashtagList)
                .build();

        return goal;
    }

}
