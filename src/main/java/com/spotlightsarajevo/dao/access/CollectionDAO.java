package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entities.CollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionDAO extends JpaRepository<CollectionEntity, Integer> {
}
