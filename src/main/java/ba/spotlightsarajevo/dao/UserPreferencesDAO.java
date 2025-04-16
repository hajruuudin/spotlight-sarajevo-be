package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.UserPreferencesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPreferencesDAO extends JpaRepository<UserPreferencesEntity, Integer> {
}
