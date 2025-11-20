package com.spotlightsarajevo.modules.spot.domain;

import com.spotlightsarajevo.modules.spot.domain.entity.SpotReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpotReviewDAO extends JpaRepository<SpotReviewEntity, Integer> {
    List<SpotReviewEntity> findAllBySpotId(Integer spotId);
}
