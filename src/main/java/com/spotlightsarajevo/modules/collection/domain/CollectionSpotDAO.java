package com.spotlightsarajevo.modules.collection.domain;

import com.spotlightsarajevo.modules.collection.domain.entity.CollectionSpotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionSpotDAO extends JpaRepository<CollectionSpotEntity, Integer> {
}
