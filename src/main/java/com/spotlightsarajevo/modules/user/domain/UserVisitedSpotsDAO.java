package com.spotlightsarajevo.modules.user.domain;

import com.spotlightsarajevo.modules.user.domain.entity.UserVisitedSpotsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVisitedSpotsDAO extends JpaRepository<UserVisitedSpotsEntity, Integer> {
}
