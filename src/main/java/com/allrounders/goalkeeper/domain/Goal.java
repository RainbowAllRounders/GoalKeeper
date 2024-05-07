package com.allrounders.goalkeeper.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer goalId;

    @Column(length = 20, nullable = false)
    private String title;

    @Column(length = 200, nullable = false)
    private String content;

    @ColumnDefault("1")
    private Integer maxPeople;

    // cur_people 컬럼 값 구할 때 사용
    @OneToMany(mappedBy = "goal")
    private List<MemberGoal> memberGoalList;

    // like_count 컬럼 값 구할 때 사용
    @OneToMany(mappedBy = "goal")
    private List<Likes> likeList;

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

    @OneToOne(mappedBy = "goal")
    private GoalImg goalImg;

    // 매핑 편의 메소드 ----------------------------------------

    public void addMemberGoal(MemberGoal memberGoal) {
        memberGoalList.add(memberGoal);
        memberGoal.setGoal(this);
    }

    public void addLike(Likes like) {
        likeList.add(like);
        like.setGoal(this);
    }

    public void setGoalImg(GoalImg goalImg) {
        this.goalImg = goalImg;
        goalImg.setGoal(this);
    }

}
