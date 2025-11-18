package com.spotlightsarajevo.modules.media.service;

import com.spotlightsarajevo.common.enums.ObjectType;

public interface LookupImagesService {
    <T> void lookupThumbnailImage(T objectModel, ObjectType objectType, Integer objectId);
    <T> void lookupAllImages(T objectModel, ObjectType objectType, Integer objectId);
}
