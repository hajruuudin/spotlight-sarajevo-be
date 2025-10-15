package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entities.EventReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventReviewDAO extends JpaRepository<EventReviewEntity, Integer> {
}
