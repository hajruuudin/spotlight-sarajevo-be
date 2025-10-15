package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entities.TransportMethodStationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportMethodStationDAO extends JpaRepository<TransportMethodStationEntity, Integer> {
}
