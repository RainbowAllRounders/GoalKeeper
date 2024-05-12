package com.allrounders.goalkeeper.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    // cur_people 컬럼 값 구할 때 사용
    @OneToMany(mappedBy = "goal")
    private List<MemberGoal> member = new ArrayList<>();

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

    public void addLikeCount(int count) {
        this.likeCount = count;
    }

//    @Column(nullable = false)
    private String imgPath;

    // 매핑 편의 메소드 ----------------------------------------

//    public void addMemberGoal(MemberGoal memberGoal) {
//        memberGoalList.add(memberGoal);
//        memberGoal.setGoal(this);
//    }

    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }

    public void setMember(List<MemberGoal> list) {
        for(MemberGoal memberGoal : list) {
            if(!this.member.contains(memberGoal)) {
                this.member.add(memberGoal);
                memberGoal.setGoal(this);
            }
        }
    }
    
    public void addHashTag(Hashtag hashtag) {
        hashtag.addGoal(this);
        hashtagList.add(hashtag);
    }
}
