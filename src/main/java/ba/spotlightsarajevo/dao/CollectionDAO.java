package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.CollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionDAO extends JpaRepository<CollectionEntity, Integer> {
    @Query("SELECT ce FROM CollectionEntity ce WHERE collectionName = :collectionName AND userId = :userId")
    CollectionEntity findByColletionName(String collectionName, Integer userId);
}
