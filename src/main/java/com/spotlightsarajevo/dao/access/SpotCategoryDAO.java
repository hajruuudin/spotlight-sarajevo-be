package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entities.SpotCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotCategoryDAO extends JpaRepository<SpotCategoryEntity, Integer> {
}
