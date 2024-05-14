package com.allrounders.goalkeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories
public class GoalkeeperApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoalkeeperApplication.class, args);
    }
}
