package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.CollectionEventEntity;
import ba.spotlightsarajevo.dao.entities.CollectionSpotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionEventDAO extends JpaRepository<CollectionEventEntity, Integer> {
    List<CollectionEventEntity> findAllByCollectionId(Integer collectionId);

    Optional<CollectionEventEntity> findByCollectionIdAndEventId(Integer collectionId, Integer eventId);
}
