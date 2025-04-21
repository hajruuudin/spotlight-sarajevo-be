package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.SpotTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotTagDAO extends JpaRepository<SpotTagEntity, Integer> {
}
