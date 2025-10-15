package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entities.MediaStoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaStoreDAO extends JpaRepository<MediaStoreEntity, Integer> {
}
