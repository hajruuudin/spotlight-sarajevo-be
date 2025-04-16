package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventDAO extends JpaRepository<EventEntity, Integer> {
}
