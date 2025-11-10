package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entity.SpotTagsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpotTagsDAO extends JpaRepository<SpotTagsEntity, Integer> {
    List<SpotTagsEntity> findBySpotId(Integer id);
}
