package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.UserVisitedSpotsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVisitedSpotsDAO extends JpaRepository<UserVisitedSpotsEntity, Integer> {
}
