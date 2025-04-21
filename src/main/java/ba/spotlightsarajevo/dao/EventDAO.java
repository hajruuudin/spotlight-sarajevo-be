package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.EventEntity;
import ba.spotlightsarajevo.dao.entities.SpotEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventDAO extends JpaRepository<EventEntity, Integer> {
    Page<EventEntity> findAll(Pageable pageable);
}
