package com.allrounders.goalkeeper.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

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

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 10, nullable = false)
    private String nickname;

    @ColumnDefault("1000")
    private Integer curPoint;
}
