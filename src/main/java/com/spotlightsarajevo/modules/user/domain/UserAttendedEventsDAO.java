package com.spotlightsarajevo.modules.user.domain;

import com.spotlightsarajevo.modules.user.domain.entity.UserAttendedEventsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAttendedEventsDAO extends JpaRepository<UserAttendedEventsEntity, Integer> {
}
