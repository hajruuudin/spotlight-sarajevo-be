package ba.spotlightsarajevo.service.definition.service;

import ba.spotlightsarajevo.dao.models.collection.CollectionCreateModel;
import ba.spotlightsarajevo.dao.models.collection.CollectionModel;
import ba.spotlightsarajevo.dao.models.collection.CollectionStatus;
import ba.spotlightsarajevo.dao.models.collection.CollectionWithItemsModel;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

public interface CollectionService {
    ResponseEntity<CollectionWithItemsModel> getCollectionByName(String collectionName, Principal principal);
    ResponseEntity<CollectionModel> addCustomCollection(CollectionCreateModel request, Principal principal);
    ResponseEntity<List<CollectionStatus>> getCollectionsWithItemPresence(Integer objectId, String objectType, Principal principal);
    ResponseEntity<CollectionStatus> addObjectToCollection(String collectionName, Integer objectId, Principal principal);
    ResponseEntity<CollectionStatus> removeObjectToCollection(String collectionName, Integer objectId, Principal principal);
}
