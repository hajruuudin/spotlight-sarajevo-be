package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserDAO extends JpaRepository<UserEntity, Integer> {
    @Transactional(readOnly = true)
    @Query("SELECT u FROM UserEntity u WHERE u.googleId = :googleId")
    UserEntity findByGoogleId(@Param("googleId") String googleId);

    @Transactional(readOnly = true)
    @Query("SELECT u FROM UserEntity u WHERE u.googleEmail = :email OR u.email = :email")
    UserEntity findByEmail(@Param("email") String email);

    @Transactional(readOnly = true)
    @Query("SELECT u FROM UserEntity u WHERE u.email = :email")
    UserEntity findBySystemEmail(@Param("email") String email);
}
