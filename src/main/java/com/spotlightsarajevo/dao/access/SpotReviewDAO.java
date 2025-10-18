package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entity.SpotReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotReviewDAO extends JpaRepository<SpotReviewEntity, Integer> {
}
