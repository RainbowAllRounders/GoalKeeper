package com.allrounders.goalkeeper.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 10, nullable = false, unique = true)
    private String nickname;

    @ColumnDefault("1000")
    private Integer curPoint;

    @Column
    private Integer rankPoint;

    @Column(nullable = false)
    private Integer ranking;

    @Column(length = 255)
    private String imgPath;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<AuthImg> authImgSet = new HashSet<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MemberGoal> memberGoalList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Likes> likesList = new ArrayList<>();


    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }


    public void updateMember(String nickname, String password) {
        if (nickname != null && !nickname.isEmpty()) {
            this.nickname = nickname;
        }
        if (password != null && !password.isEmpty()) {
            this.password = password;
        }

        if (this.curPoint >= 500) {
            this.curPoint -= 500;
        } else {
            throw new IllegalStateException();
        }
    }

    public void updateRank(Integer ranking) {
        this.ranking = ranking;
    }

}
