package com.allrounders.goalkeeper.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authImgId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")   // foreign key (member_id) references Member (member_id)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goalId")   // foreign key (goal_id) references Goal (goal_id)
    private Goal goal;

    @Column(nullable = false)
    private String imgPath;

    // 값이 입력될 때 자동으로 현재 시간이 들어감
    @CreationTimestamp
    private LocalDate updateDate;
}
