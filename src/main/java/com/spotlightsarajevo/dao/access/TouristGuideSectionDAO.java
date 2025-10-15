package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entity.TouristGuideSectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TouristGuideSectionDAO extends JpaRepository<TouristGuideSectionEntity, Integer> {
}
