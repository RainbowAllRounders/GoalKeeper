package com.allrounders.goalkeeper.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProfileImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer profile_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")   // foreign key (member_id) references Member (member_id)
    private Member member;

    @Column(nullable = false)
    private String imgPath;

    @Column(nullable = false)
    private String imgName;

    @Column(nullable = false)
    private String imgType;

    // 20MB를 포용하기 위해 Long 타입 사용
    @Column(nullable = false)
    private Long imgSize;

    @CreationTimestamp  // 값이 입력될 때 자동으로 현재 시간이 들어감
    @UpdateTimestamp  // 값이 수정될 때 자동으로 현재 시간이 들어감
    private LocalDate update_date;
}