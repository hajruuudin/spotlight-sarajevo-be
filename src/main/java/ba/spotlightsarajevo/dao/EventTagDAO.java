package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.EventTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTagDAO extends JpaRepository<EventTagEntity, Integer> {
}
