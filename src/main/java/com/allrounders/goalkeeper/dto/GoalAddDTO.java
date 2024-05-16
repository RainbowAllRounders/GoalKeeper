package com.allrounders.goalkeeper.dto;

import com.allrounders.goalkeeper.domain.Goal;
import com.allrounders.goalkeeper.domain.Hashtag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
    private String complete = "모집 중";

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private String hashtagDTOs; // 프론트에서 받아오는 해시태그들의 tagName이 연결된 문자열
  
    private List<HashtagDTO> hashtagDTOList = new ArrayList<>();    // hashtagDTOs를 HashtagDTO 형태로 변환

    public Goal dtoToEntity() {

        changeList(hashtagDTOs);
        List<Hashtag> hashtagList = this.hashtagDTOList.stream()
                .map(HashtagDTO::dtoToEntity)
                .collect(Collectors.toList());
        System.out.println(this.toString());
        return Goal.builder()
                .title(this.title)
                .content(this.content)
                .maxPeople(this.maxPeople)
                .authCount(this.authCount)
                .hashtagList(hashtagList)
                .complete(this.complete)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }

    public void changeList(String hashtagDTOs) {
        if (hashtagDTOs != null) {

            String[] split = hashtagDTOs.split("\\s*#\\s*");

            for (String tagName : split) {
                HashtagDTO hash = HashtagDTO.builder().tagName(tagName).build();
                hashtagDTOList.add(hash);
            }
        }
    }


}
