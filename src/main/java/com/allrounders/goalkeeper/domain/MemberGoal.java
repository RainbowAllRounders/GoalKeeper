package com.allrounders.goalkeeper.domain;

import jakarta.validation.constraints.Future;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberGoalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goalId")   // foreign key (goal_id) references Goal (goal_id)
    private Goal goal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")   // foreign key (member_id) references Member (member_id)
    private Member member;

    @Future
    @Column(nullable = false)
    private LocalDate startAlarmDate;

    @Future
    @Column(nullable = false)
    private LocalDate endAlarmDate;

    @ColumnDefault("false")
    private Boolean isChecked;

    @ColumnDefault("false")
    private Boolean isSuccess;
}
