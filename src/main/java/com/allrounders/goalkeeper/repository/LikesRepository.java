package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long>, LikesCustomRepository {
}
