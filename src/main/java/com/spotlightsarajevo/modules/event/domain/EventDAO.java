package com.spotlightsarajevo.modules.event.domain;

import com.spotlightsarajevo.modules.event.domain.entity.EventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EventDAO extends JpaRepository<EventEntity, Integer>, JpaSpecificationExecutor<EventEntity> {
    Page<EventEntity> findAll(Pageable query);
}
