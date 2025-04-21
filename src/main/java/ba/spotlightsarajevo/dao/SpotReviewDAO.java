package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.SpotReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotReviewDAO extends JpaRepository<SpotReviewEntity, Integer> {
}
