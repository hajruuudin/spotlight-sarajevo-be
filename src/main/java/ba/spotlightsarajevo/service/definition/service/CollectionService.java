package ba.spotlightsarajevo.service.definition.service;

import ba.spotlightsarajevo.dao.models.collection.CollectionCreateModel;
import ba.spotlightsarajevo.dao.models.collection.CollectionModel;
import ba.spotlightsarajevo.dao.models.collection.CollectionWithItemsModel;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface CollectionService {
    ResponseEntity<CollectionWithItemsModel> getCollectionByName(String collectionName, Principal principal);
    ResponseEntity<CollectionModel> addCustomCollection(CollectionCreateModel request, Principal principal);
}
