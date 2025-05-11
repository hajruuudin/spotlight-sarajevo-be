package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.CollectionEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionEventDAO extends JpaRepository<CollectionEventEntity, Integer> {
    List<CollectionEventEntity> findAllByCollectionId(Integer collectionId);
}
