package com.spotlightsarajevo.modules.auth.domain;

import com.spotlightsarajevo.modules.auth.domain.entity.UserAttendedEventsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAttendedEventsDAO extends JpaRepository<UserAttendedEventsEntity, Integer> {
    Integer countAllByUserId(Integer id);
}
