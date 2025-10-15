package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entities.EventOrganiserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventOrganiserDAO extends JpaRepository<EventOrganiserEntity, Integer> {
}
