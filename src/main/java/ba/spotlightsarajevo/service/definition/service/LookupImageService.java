package ba.spotlightsarajevo.service.definition.service;

import ba.spotlightsarajevo.enums.ObjectType;

public interface LookupImageService {
    <T> void lookupThumbnailImage(T objectModel, ObjectType objectType, Integer objectId);
    <T> void lookupAllImages(T objectModel, ObjectType objectType, Integer objectId);
}
