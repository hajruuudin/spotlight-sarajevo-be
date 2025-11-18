package com.spotlightsarajevo.modules.transport.domain;

import com.spotlightsarajevo.modules.transport.domain.entity.TransportMethodStationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportMethodStationDAO extends JpaRepository<TransportMethodStationEntity, Integer> {
}
