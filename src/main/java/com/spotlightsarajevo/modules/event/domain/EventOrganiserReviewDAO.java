package com.spotlightsarajevo.modules.event.domain;

import com.spotlightsarajevo.modules.event.domain.entity.EventOrganiserReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventOrganiserReviewDAO extends JpaRepository<EventOrganiserReviewEntity, Integer> {
}
