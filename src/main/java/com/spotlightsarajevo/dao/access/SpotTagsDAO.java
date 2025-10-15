package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entities.SpotTagsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotTagsDAO extends JpaRepository<SpotTagsEntity, Integer> {
}
