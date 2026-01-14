package com.spotlightsarajevo.modules.media.service;

import com.spotlightsarajevo.common.enums.ExceptionCodes;
import com.spotlightsarajevo.common.enums.ObjectType;
import com.spotlightsarajevo.common.exceptions.MediaExceptions;
import com.spotlightsarajevo.modules.media.api.dto.MediaStoreCreateModel;
import com.spotlightsarajevo.modules.media.api.dto.MediaStoreModel;
import com.spotlightsarajevo.modules.media.domain.MediaStoreDAO;
import com.spotlightsarajevo.modules.media.domain.entity.MediaStoreEntity;
import com.spotlightsarajevo.modules.media.mapper.MediaStoreMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Service
@AllArgsConstructor
public class MediaServiceImpl implements MediaService {
    MediaStoreDAO mediaStoreDAO;
    MediaStoreMapper mediaStoreMapper;

    @Override
    public <T> void lookupThumbnailImage(T objectModel, ObjectType objectType, Integer objectId) {
        if (objectModel == null || objectType == null || objectId == null) {
            return;
        }

        String thumbnailUrl = mediaStoreDAO.findThumbnailUrlByItemIdAndItemCategory(objectId, objectType);

        if (thumbnailUrl != null) {
            try {
                Method setImageURLMethod = objectModel.getClass().getMethod("setThumbnailImage", String.class);
                setImageURLMethod.invoke(objectModel, thumbnailUrl);
            } catch (NoSuchMethodException e) {
                System.err.println("Object of type " + objectModel.getClass().getName() + " does not have a setThumbnailImage(String) method.");
            } catch (Exception e) {
                System.err.println("Error invoking setImageUrl method on " + objectModel.getClass().getName() + ": " + e.getMessage());
            }
        } else {
            try {
                Method setImageURLMethod = objectModel.getClass().getMethod("setThumbnailImage", String.class);
                setImageURLMethod.invoke(objectModel, "https://i.ibb.co/W472hxLk/FALLBACK.png");
            } catch (NoSuchMethodException e) {

            } catch (Exception e) {

            }
        }
    }

    @Override
    public <T> void lookupAllImages(T objectModel, ObjectType objectType, Integer objectId) {

    }

    @Override
    public ResponseEntity<MediaStoreModel> create(MediaStoreCreateModel request) {
        if(request == null) throw new MediaExceptions.MediaCreateException(ExceptionCodes.MediaExceptionCodes.MEDIA_C_BR);

        MediaStoreEntity entity = mediaStoreMapper.dtoToEntity(request);
        MediaStoreEntity addedEntity = mediaStoreDAO.save(entity);

        return ResponseEntity.status(200).body(mediaStoreMapper.entityToDto(addedEntity));
    }
}
