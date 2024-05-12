package com.allrounders.goalkeeper.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    // 재원
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goalId")   // foreign key (goal_id) references Goal (goal_id)
    private Goal goal;
    
    // 혜리
    @Column(nullable = false)
    private Long goalId;

    @Column(length = 8, nullable = false)
    private String tagName;

    public void addGoal(Goal goal) {
        this.goal = goal;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
