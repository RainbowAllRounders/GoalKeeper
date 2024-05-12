package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.domain.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long>, HashtagCustomRepository {
}
