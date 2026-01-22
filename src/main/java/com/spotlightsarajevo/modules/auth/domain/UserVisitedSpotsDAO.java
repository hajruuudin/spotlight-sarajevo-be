package com.spotlightsarajevo.modules.auth.domain;

import com.spotlightsarajevo.modules.auth.domain.entity.UserVisitedSpotsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVisitedSpotsDAO extends JpaRepository<UserVisitedSpotsEntity, Integer> {
    Integer countAllByUserId(Integer id);

    void deleteByUserIdAndSpotId(Integer userId, Integer spotId);
}
