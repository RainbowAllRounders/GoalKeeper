package com.allrounders.goalkeeper.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class OXGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer oxId;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String commentary;

    @Column
    private Boolean isCorrect;
}
