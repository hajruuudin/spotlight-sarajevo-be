package com.spotlightsarajevo.modules.spot.domain;

import com.spotlightsarajevo.modules.spot.domain.entity.SpotTagsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpotTagsDAO extends JpaRepository<SpotTagsEntity, Integer> {
    List<SpotTagsEntity> findBySpotId(Integer id);
}
