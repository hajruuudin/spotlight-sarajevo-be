package com.spotlightsarajevo.modules.media.service;

import com.spotlightsarajevo.common.enums.ObjectType;
import com.spotlightsarajevo.modules.media.api.dto.MediaStoreCreateModel;
import com.spotlightsarajevo.modules.media.api.dto.MediaStoreModel;
import org.springframework.http.ResponseEntity;

public interface MediaService {
    <T> void lookupThumbnailImage(T objectModel, ObjectType objectType, Integer objectId);
    <T> void lookupAllImages(T objectModel, ObjectType objectType, Integer objectId);
    ResponseEntity<MediaStoreModel> create(MediaStoreCreateModel request);
}
