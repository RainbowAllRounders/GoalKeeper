package com.allrounders.goalkeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GoalkeeperApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoalkeeperApplication.class, args);
    }
}
