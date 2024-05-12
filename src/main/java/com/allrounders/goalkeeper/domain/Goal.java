package com.allrounders.goalkeeper.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

//    @OneToMany(mappedBy = "goal")
//    private List<Hashtag> hashtagList;

    //    @Column(nullable = false)
    private String imgPath;

    public void addLikeCount(int count) {
        this.likeCount = count;
    }

    public void addCurPeople(int count) {
        this.curPeople = count;
    }

    // 매핑 편의 메소드 ----------------------------------------

//    public void addMemberGoal(MemberGoal memberGoal) {
//        memberGoalList.add(memberGoal);
//        memberGoal.setGoal(this);
//    }

    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }
    
//    public void addHashTag(Hashtag hashtag) {
//        hashtagList.add(hashtag);
//        hashtag.setGoal(this);
//    }
}
