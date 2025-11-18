package com.spotlightsarajevo.modules.collection.domain;

import com.spotlightsarajevo.modules.collection.domain.entity.CollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionDAO extends JpaRepository<CollectionEntity, Integer> {
}
