package com.allrounders.goalkeeper.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@ToString(exclude = {"memberGoalList", "hashtagList"})
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goalId;

    @Column(length = 20, nullable = false)
    private String title;

    @Column(length = 200, nullable = false)
    private String content;

    @ColumnDefault("1")
    private Integer maxPeople;

    @ColumnDefault("1")
    private Integer curPeople;

    @Column(nullable = false)
    private int likeCount;

    @ColumnDefault("0")
    private Integer authCount;

    @ColumnDefault("모집 중")
    private String complete;

    @Future
    @Column(nullable = false)
    private LocalDate startDate;

    @Future
    @Column(nullable = false)
    private LocalDate endDate;

    @CreationTimestamp  // 값이 입력될 때 자동으로 현재 시간이 들어감
    private LocalDate createDate;

    @OneToMany(mappedBy = "goal")
    private List<Hashtag> hashtagList;

//    @Column(nullable = false)
    private String imgPath;

//    @OneToMany(mappedBy = "goal")
//    private List<MemberGoal> memberGoalList;

    /**
     * 좋아요 추가
     */
    public void addLikeCount(int count) {
        this.likeCount = count;
    }

    /**
     * 참여인원 추가
     */
    public void addCurPeople() {
        this.curPeople++;
    }

    /**
     * 참여인원 차감
     */
    public void minusCurPeople() {
        this.curPeople--;
    }
  
    /**
     * 연관관계 편의 메서드
     */
    public Goal addHashTag(Hashtag hashtag) {
        hashtag.addGoal(this);
        hashtagList.add(hashtag);
        return this;
    }
}
