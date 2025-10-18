package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entity.UserAuthDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthDetailsDAO extends JpaRepository<UserAuthDetailsEntity, Integer> {
}
