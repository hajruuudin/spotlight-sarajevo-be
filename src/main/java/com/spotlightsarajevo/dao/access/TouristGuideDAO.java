package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entities.TouristGuideEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TouristGuideDAO extends JpaRepository<TouristGuideEntity, Integer> {
}
