package com.spotlightsarajevo.modules.event.domain;

import com.spotlightsarajevo.modules.event.domain.entity.EventReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventReviewDAO extends JpaRepository<EventReviewEntity, Integer> {
}
