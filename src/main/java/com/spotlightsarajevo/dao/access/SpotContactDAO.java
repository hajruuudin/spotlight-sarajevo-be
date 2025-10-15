package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entity.SpotContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotContactDAO extends JpaRepository<SpotContactEntity, Integer> {
}
