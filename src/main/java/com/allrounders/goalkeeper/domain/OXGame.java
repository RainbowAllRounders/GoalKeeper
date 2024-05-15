package com.allrounders.goalkeeper.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OXGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oxId;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String commentary;

    @Column
    private Boolean isCorrect;
}
