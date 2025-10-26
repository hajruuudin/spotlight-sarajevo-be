package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entity.UserFavouriteSpotsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFavouriteSpotsDAO extends JpaRepository<UserFavouriteSpotsEntity, Integer> {
}
