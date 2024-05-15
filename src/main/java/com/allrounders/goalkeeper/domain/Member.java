package com.allrounders.goalkeeper.domain;

import com.allrounders.goalkeeper.dto.MyPageModifyDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
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

    @ColumnDefault("0")
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

    public void addPoint() {
        this.curPoint += 500;
    }

    public void minusPoint() {
        this.curPoint -= 500;
    }

    public void updateMember(MyPageModifyDTO modifyDTO) {
        if (modifyDTO.getNickname() != null && !modifyDTO.getNickname().isEmpty()) {
            this.nickname = modifyDTO.getNickname();
        }
        if (modifyDTO.getPassword() != null && !modifyDTO.getPassword().isEmpty()) {
            this.password = modifyDTO.getPassword();
        }
        if (modifyDTO.getImgPath() != null && !modifyDTO.getImgPath().isEmpty()) {
            this.imgPath = modifyDTO.getImgPath();
        }

        if (this.curPoint >= 500) {
            minusPoint();
        } else {
            throw new IllegalStateException();
        }
    }

    public void updateRank(Integer ranking) {
        this.ranking = ranking;
    }

    public void updateCurPointAddGoal() {
        if(this.curPoint >= 500) this.curPoint -= 500;
        else throw new IllegalStateException();
    }
}
