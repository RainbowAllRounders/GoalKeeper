package com.allrounders.goalkeeper.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goalId")   // foreign key (goal_id) references Goal (goal_id)
    private Goal goal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")   // foreign key (member_id) references Member (member_id)
    private Member member;

    @ColumnDefault("false")
    private Boolean isLiked;

    // 매핑 편의 메소드 ----------------------------------------

    public void setGoal(Goal goal) {
        this.goal = goal;
        goal.getLikeList().add(this);
    }
}
