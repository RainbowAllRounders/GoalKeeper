package com.allrounders.goalkeeper.domain;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
