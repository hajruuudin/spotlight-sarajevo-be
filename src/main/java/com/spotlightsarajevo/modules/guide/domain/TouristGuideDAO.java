package com.spotlightsarajevo.modules.guide.domain;

import com.spotlightsarajevo.modules.guide.domain.entity.TouristGuideEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TouristGuideDAO extends JpaRepository<TouristGuideEntity, Integer> {
    Optional<TouristGuideEntity> findBySlug(String slug);
    List<TouristGuideEntity> findByCategoryId(Integer id);
}
