package com.spotlightsarajevo.modules.user.domain;

import com.spotlightsarajevo.modules.user.domain.entity.UserFavouriteSpotsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFavouriteSpotsDAO extends JpaRepository<UserFavouriteSpotsEntity, Integer> {
}
