package com.spotlightsarajevo.modules.media.utils;

import com.spotlightsarajevo.common.enums.ObjectType;
import com.spotlightsarajevo.modules.media.api.dto.MediaStoreCreateModel;
import com.spotlightsarajevo.modules.media.domain.MediaStoreDAO;
import com.spotlightsarajevo.modules.media.domain.entity.MediaStoreEntity;
import com.spotlightsarajevo.modules.media.service.MediaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class MediaUtilities {

    private final MediaStoreDAO mediaStoreDAO;
    private final MediaService mediaService;

    /**
     * Updates the thumbnail image for a given item. Deletes the existing thumbnail and creates a new one.
     *
     * @param thumbnailModel the new thumbnail image model
     * @param itemId         the ID of the item
     * @param objectType     the type of the object (e.g., SPOT, EVENT, ORGANISER)
     * @param username       the username of the user performing the update
     */
    public void updateThumbnailImage(MediaStoreCreateModel thumbnailModel, Integer itemId, ObjectType objectType, String username) {
        if (thumbnailModel == null) return;

        mediaStoreDAO.deleteByItemIdAndItemCategoryAndIsThumbnailTrue(itemId, objectType);

        thumbnailModel.setItemId(itemId);
        thumbnailModel.setItemCategory(objectType.toString());
        thumbnailModel.setIsThumbnail(true);
        thumbnailModel.setCreated(LocalDateTime.now());
        thumbnailModel.setCreatedBy(username);
        mediaService.create(thumbnailModel);
    }

    /**
     * Adds new images for a given item.
     *
     * @param imagesToAdd the list of images to add
     * @param itemId      the ID of the item
     * @param objectType  the type of the object (e.g., SPOT, EVENT)
     * @param username    the username of the user performing the operation
     */
    public void addImages(List<MediaStoreCreateModel> imagesToAdd, Integer itemId, ObjectType objectType, String username) {
        if (imagesToAdd == null || imagesToAdd.isEmpty()) return;

        for (MediaStoreCreateModel imageModel : imagesToAdd) {
            imageModel.setItemId(itemId);
            imageModel.setItemCategory(objectType.toString());
            imageModel.setIsThumbnail(false);
            imageModel.setCreated(LocalDateTime.now());
            imageModel.setCreatedBy(username);
            mediaService.create(imageModel);
        }
    }

    /**
     * Deletes images by their IDs.
     *
     * @param imageIdsToRemove the list of image IDs to remove
     */
    public void deleteImages(List<Integer> imageIdsToRemove) {
        if (imageIdsToRemove == null || imageIdsToRemove.isEmpty()) return;

        for (Integer imageId : imageIdsToRemove) {
            Optional<MediaStoreEntity> imageEntity = mediaStoreDAO.findById(imageId);
            if (imageEntity.isPresent()) {
                mediaStoreDAO.deleteById(imageId);
            }
        }
    }
}
