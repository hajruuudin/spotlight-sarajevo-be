package com.spotlightsarajevo.modules.collection.domain;

import com.spotlightsarajevo.common.utils.CollectionItem;
import com.spotlightsarajevo.modules.collection.domain.entity.CollectionEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionEventDAO extends JpaRepository<CollectionEventEntity, Integer> {
    List<CollectionItem> findAllByCollectionId(Integer collectionId);
    Optional<CollectionEventEntity> findByCollectionIdAndEventId(Integer collectionId, Integer eventId);
    List<CollectionEventEntity> findAllByEventIdAndCollection_UserId(Integer eventId, Integer userId);
}
