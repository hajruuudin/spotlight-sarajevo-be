package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<UserEntity, Integer> {
    @Query("SELECT u FROM UserEntity u WHERE u.sysEmail = :email OR u.googleEmail = :email")
    UserEntity findBySysEmailOrGoogleEmail(@Param("email") String email);

}
