package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.SpotTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpotTagDAO extends JpaRepository<SpotTagEntity, Integer> {
    @Query("SELECT se FROM SpotTagEntity se WHERE se.spotId = :id")
    List<SpotTagEntity> findAllTagsById(Integer id);
    void deleteAllBySpotId(Integer id);
}
