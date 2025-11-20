package com.spotlightsarajevo.modules.spot.domain;

import com.spotlightsarajevo.modules.spot.domain.entity.SpotWorkHoursEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpotWorkHoursDAO extends JpaRepository<SpotWorkHoursEntity, Integer> {
    List<SpotWorkHoursEntity> findAllBySpotId(Integer spotId);
}
