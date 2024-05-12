package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.domain.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
}
