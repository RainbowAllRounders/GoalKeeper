package com.allrounders.goalkeeper.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.transaction.annotation.Transactional;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 10, nullable = false)
    private String nickname;

    @ColumnDefault("1000")
    private Integer curPoint;

    @Column
    private Integer todayPoint;

    @Column
    private Integer rankPoint;

    @Column
    private Integer ranks;

    @Column
    private String imgPath;


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

}
