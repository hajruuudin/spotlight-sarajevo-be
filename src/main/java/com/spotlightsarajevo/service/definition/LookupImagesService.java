package com.spotlightsarajevo.service.definition;

import com.spotlightsarajevo.utils.enums.ObjectType;

public interface LookupImagesService {
    <T> void lookupThumbnailImage(T objectModel, ObjectType objectType, Integer objectId);
    <T> void lookupAllImages(T objectModel, ObjectType objectType, Integer objectId);
}
