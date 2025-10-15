package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entities.TransportMethodOperatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportMethodOperatorDAO extends JpaRepository<TransportMethodOperatorEntity, Integer> {
}
