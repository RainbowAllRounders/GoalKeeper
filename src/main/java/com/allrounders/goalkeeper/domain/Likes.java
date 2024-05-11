package com.allrounders.goalkeeper.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"goal", "member"})
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goalId")   // foreign key (goal_id) references Goal (goal_id)
    private Goal goal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")   // foreign key (member_id) references Member (member_id)
    private Member member;

    @ColumnDefault("false")
    private Boolean isLiked;

    public Likes(Member member, Goal goal, boolean likedStatus) {
        this.member = member;
        this.goal = goal;
        this.isLiked = likedStatus;
    }

    /**
     * 좋아요 클릭
     */
    public static Likes insertLike(Member member, Goal goal, Boolean isLiked) {
        return Likes.builder()
                .member(member)
                .goal(goal)
                .isLiked(true)
                .build();
    }

    /**
     * 좋아요 true -> false
     *      false -> true
     */
    public void changeLikeStatus(Boolean isLiked) {
        if(isLiked == true) this.isLiked = false;
        else this.isLiked = true;
    }
    // 매핑 편의 메소드 ----------------------------------------
//
//    public void setGoal(Goal goal) {
//        this.goal = goal;
//        goal.getLikeList().add(this);
//    }
}
