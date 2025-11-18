package com.spotlightsarajevo.modules.collection.domain;

import com.spotlightsarajevo.modules.collection.domain.entity.CollectionEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionEventDAO extends JpaRepository<CollectionEventEntity, Integer> {
}
