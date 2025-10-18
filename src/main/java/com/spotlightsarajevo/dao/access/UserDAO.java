package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<UserEntity, Integer> {
}
