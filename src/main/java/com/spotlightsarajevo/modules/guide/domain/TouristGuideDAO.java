package com.spotlightsarajevo.modules.guide.domain;

import com.spotlightsarajevo.modules.guide.domain.entity.TouristGuideEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TouristGuideDAO extends JpaRepository<TouristGuideEntity, Integer> {
}
