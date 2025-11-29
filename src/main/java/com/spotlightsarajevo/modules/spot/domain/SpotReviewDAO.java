package com.spotlightsarajevo.modules.spot.domain;

import com.spotlightsarajevo.modules.spot.api.dto.SpotReviewModel;
import com.spotlightsarajevo.modules.spot.domain.entity.SpotReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpotReviewDAO extends JpaRepository<SpotReviewEntity, Integer>, JpaSpecificationExecutor<SpotReviewEntity> {
    Page<SpotReviewEntity> findAll(Pageable request);
}
