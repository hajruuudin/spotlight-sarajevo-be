package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entities.EventTagsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTagsDAO extends JpaRepository<EventTagsEntity, Integer> {
}
