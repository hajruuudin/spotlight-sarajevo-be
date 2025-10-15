package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entity.EventCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventCategoryDAO extends JpaRepository<EventCategoryEntity, Integer> {
}
