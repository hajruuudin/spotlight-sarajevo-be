package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entity.UserFavouriteEventsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFavouriteEventsDAO extends JpaRepository<UserFavouriteEventsEntity, Integer> {
}
