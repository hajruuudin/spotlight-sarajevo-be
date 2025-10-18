package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entity.SpotEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotDAO extends JpaRepository<SpotEntity, Integer>{
    Page<SpotEntity> findAll(Pageable query);
}
