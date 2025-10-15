package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entities.TransportMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportMethodDAO extends JpaRepository<TransportMethodEntity, Integer> {
}
