package com.spotlightsarajevo.modules.user.domain;

import com.spotlightsarajevo.modules.user.domain.entity.UserFavouriteEventsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFavouriteEventsDAO extends JpaRepository<UserFavouriteEventsEntity, Integer> {
}
