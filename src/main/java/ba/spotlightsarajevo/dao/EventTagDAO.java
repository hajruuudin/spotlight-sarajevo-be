package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.EventTagEntity;
import ba.spotlightsarajevo.dao.entities.SpotTagEntity;
import jdk.jfr.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventTagDAO extends JpaRepository<EventTagEntity, Integer> {
    @Query("SELECT ee FROM EventTagEntity ee WHERE ee.eventId = :id")
    List<EventTagEntity> findAllTagsById(Integer id);
    void deleteAllByEventId(Integer id);
}
