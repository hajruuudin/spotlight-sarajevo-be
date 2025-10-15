package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entities.SpotReviewStatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotReviewStatsDAO extends JpaRepository<SpotReviewStatsEntity, Integer> {
}
