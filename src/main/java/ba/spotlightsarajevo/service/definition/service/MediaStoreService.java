package ba.spotlightsarajevo.service.definition.service;

import ba.spotlightsarajevo.dao.entities.MediaStoreEntity;
import ba.spotlightsarajevo.dao.models.mediastore.MediaStoreCreate;
import ba.spotlightsarajevo.dao.models.mediastore.MediaStoreModel;
import org.springframework.http.ResponseEntity;

public interface MediaStoreService {
    ResponseEntity<MediaStoreEntity> create(MediaStoreCreate request);
}
