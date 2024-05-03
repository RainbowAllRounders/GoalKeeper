package com.allrounders.goalkeeper.domain;

import jakarta.validation.constraints.Future;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

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
    @OneToMany(mappedBy = "memberGoal")
    @JoinColumn(name = "memberGoalId")
    private MemberGoal memberGoal;

    // like_count 컬럼 값 구할 때 사용
    @OneToMany(mappedBy = "like")
    @JoinColumn(name = "likeId")
    private Likes like;

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
}
