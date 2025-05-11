package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.CollectionSpotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionSpotDAO extends JpaRepository<CollectionSpotEntity, Integer> {
    List<CollectionSpotEntity> findAllByCollectionId(Integer collectionId);
}
