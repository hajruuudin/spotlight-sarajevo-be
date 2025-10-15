package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entities.CollectionEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionEventDAO extends JpaRepository<CollectionEventEntity, Integer> {
}
