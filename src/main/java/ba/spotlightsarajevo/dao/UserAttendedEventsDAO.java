package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.UserAttendedEventsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAttendedEventsDAO extends JpaRepository<UserAttendedEventsEntity, Integer> {
}
