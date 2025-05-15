package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.SpotReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpotReviewDAO extends JpaRepository<SpotReviewEntity, Integer> {
    @Query("SELECT sre FROM SpotReviewEntity sre WHERE spotId = :id")
    List<SpotReviewEntity> findAllBySpotId(Integer id);
}
