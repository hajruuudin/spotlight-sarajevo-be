package com.spotlightsarajevo.modules.guide.domain;

import com.spotlightsarajevo.modules.guide.domain.entity.TouristGuideSectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TouristGuideSectionDAO extends JpaRepository<TouristGuideSectionEntity, Integer> {
}
