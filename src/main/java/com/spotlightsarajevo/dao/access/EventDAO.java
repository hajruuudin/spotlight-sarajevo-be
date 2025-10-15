package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventDAO extends JpaRepository<EventEntity, Integer> {
}
