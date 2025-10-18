package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entity.UserVisitedSpotsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVisitedSpotsDAO extends JpaRepository<UserVisitedSpotsEntity, Integer> {
}
