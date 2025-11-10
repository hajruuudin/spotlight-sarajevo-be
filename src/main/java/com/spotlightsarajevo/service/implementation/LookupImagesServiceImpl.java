package com.spotlightsarajevo.service.implementation;


import com.spotlightsarajevo.dao.access.MediaStoreDAO;
import com.spotlightsarajevo.service.definition.LookupImagesService;
import com.spotlightsarajevo.utils.enums.ObjectType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Service
@AllArgsConstructor
public class LookupImagesServiceImpl implements LookupImagesService {
    MediaStoreDAO mediaStoreDAO;

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
                Method setImageURLMethod = objectModel.getClass().getMethod("setImageUrl", String.class);
                setImageURLMethod.invoke(objectModel, "/https://i.ibb.co/W472hxLk/FALLBACK.png");
            } catch (NoSuchMethodException e) {

            } catch (Exception e) {

            }
        }
    }

    @Override
    public <T> void lookupAllImages(T objectModel, ObjectType objectType, Integer objectId) {

    }
}
