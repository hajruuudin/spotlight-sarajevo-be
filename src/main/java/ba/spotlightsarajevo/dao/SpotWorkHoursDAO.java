package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.SpotWorkHoursEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpotWorkHoursDAO extends JpaRepository<SpotWorkHoursEntity, Integer> {
    @Query("SELECT swhe FROM SpotWorkHoursEntity swhe WHERE spotId = :id")
    List<SpotWorkHoursEntity> findAllBySpotId(Integer id);
}
