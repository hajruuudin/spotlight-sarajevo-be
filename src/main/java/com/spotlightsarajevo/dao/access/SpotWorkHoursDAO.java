package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entity.SpotWorkHoursEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotWorkHoursDAO extends JpaRepository<SpotWorkHoursEntity, Integer> {
}
