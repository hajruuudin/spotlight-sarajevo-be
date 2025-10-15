package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entity.CollectionSpotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionSpotDAO extends JpaRepository<CollectionSpotEntity, Integer> {
}
