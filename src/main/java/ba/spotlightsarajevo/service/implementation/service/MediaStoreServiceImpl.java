package ba.spotlightsarajevo.service.implementation.service;

import ba.spotlightsarajevo.dao.MediaStoreDAO;
import ba.spotlightsarajevo.dao.entities.MediaStoreEntity;
import ba.spotlightsarajevo.dao.models.mediastore.MediaStoreCreate;
import ba.spotlightsarajevo.dao.models.mediastore.MediaStoreModel;
import ba.spotlightsarajevo.enums.ObjectType;
import ba.spotlightsarajevo.service.definition.service.MediaStoreService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MediaStoreServiceImpl implements MediaStoreService {
    MediaStoreDAO mediaDAO;

    @Override
    @Transactional
    public ResponseEntity<MediaStoreEntity> create(MediaStoreCreate request) {
        try{
            MediaStoreEntity mediaEntity = new MediaStoreEntity();

            mediaEntity.setItemId(request.getItemId());
            mediaEntity.setImageUrl(request.getImageUrl());
            mediaEntity.setItemCategory(ObjectType.valueOf(request.getItemCategory()));
            mediaEntity.setIsThumbnail(true);

            mediaDAO.save(mediaEntity);

            return ResponseEntity.ok(mediaEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Media Create Error");
        }
    }
}
