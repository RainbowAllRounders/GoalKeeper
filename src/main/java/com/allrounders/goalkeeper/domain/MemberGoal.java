package com.allrounders.goalkeeper.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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
    private Long memberGoalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goalId")   // foreign key (goal_id) references Goal (goal_id)
    private Goal goal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")   // foreign key (member_id) references Member (member_id)
    private Member member;

    @ColumnDefault("true")
    private Boolean role;

    // startAlarmDate = Goal의 startDate
    // 시작일 하루 알림
    @Future
    @Column(nullable = false)
    private LocalDate startAlarmDate;

    // endAlarmDate = Goal의 endDate
    // 끝나기 하루 전, 끝나는 날 총 2번 알림
    @Future
    @Column(nullable = false)
    private LocalDate endAlarmDate;

    // 알람 확인 여부
    @ColumnDefault("false")
    private Boolean isChecked;

    // 성공 확인 여부
    @ColumnDefault("false")
    private Boolean isSuccess;

    // is_success 컬럼 값 구할 때 사용
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authImgId")
    private AuthImg authImg;

    public MemberGoal(Long memberId, Long goalId, Boolean role,
                      LocalDate startAlarmDate, LocalDate endAlarmDate, Boolean isChecked) {

        this.member.setMemberId(memberId);
        this.goal.setGoalId(goalId);
        this.role = role;
        this.startAlarmDate = startAlarmDate;
        this.endAlarmDate = endAlarmDate;
        this.isChecked = isChecked;
    }

    // 매핑 편의 메소드 ----------------------------------------

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

}
