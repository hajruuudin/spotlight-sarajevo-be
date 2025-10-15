package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entity.SpotCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotCategoryDAO extends JpaRepository<SpotCategoryEntity, Integer> {
}
