package ba.spotlightsarajevo.rest;

import ba.spotlightsarajevo.dao.entities.CollectionEntity;
import ba.spotlightsarajevo.dao.models.collection.CollectionCreateModel;
import ba.spotlightsarajevo.dao.models.collection.CollectionModel;
import ba.spotlightsarajevo.dao.models.collection.CollectionStatus;
import ba.spotlightsarajevo.dao.models.collection.CollectionWithItemsModel;
import ba.spotlightsarajevo.service.definition.service.CollectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "Collection", description = "Collection API")
@RequestMapping(value = "/collection")
@AllArgsConstructor
@RestController
public class CollectionRestController {
    CollectionService collectionService;

    @Operation(description = "Get a collection from the database based on its name and user, including the items within it")
    @GetMapping(value = "collection-by-name")
    public ResponseEntity<CollectionWithItemsModel> getCollectionByName(
            @RequestParam(value = "collectionName", required = true) String collectionName,
            Principal principal
    ){
        return collectionService.getCollectionByName(collectionName, principal);
    }

    @Operation(description = "Add a new custom collection for the user")
    @PostMapping(value = "custom")
    public ResponseEntity<CollectionModel> addCustomCollection(@RequestBody CollectionCreateModel request, Principal principal){
        return collectionService.addCustomCollection(request, principal);
    }

    @Operation(description = "Gets all the collections for a user and the status of the selected spot within it")
    @GetMapping(value = "all")
    public ResponseEntity<List<CollectionStatus>> getCollectionsWithItemPresence(
            @RequestParam(value = "objectId", required = true) Integer objectId,
            @RequestParam(value = "objectType", required = true) String objectType,
            Principal principal
    ){
        return collectionService.getCollectionsWithItemPresence(objectId, objectType, principal);
    }

    @Operation(description = "Add an item to its respective collection")
    @PostMapping(value = "addObject")
    public ResponseEntity<CollectionStatus> addObjectToCollection(
            @RequestParam(value = "collectionName", required = true) String collectionName,
            @RequestParam(value = "objectId", required = true) Integer objectId,
            Principal principal
    ){
        return collectionService.addObjectToCollection(collectionName, objectId, principal);
    }

    @Operation(description = "Removes and item from its respective collection")
    @DeleteMapping(value = "removeObject")
    public ResponseEntity<CollectionStatus> removeObjectToCollection(
            @RequestParam(value = "collectionName", required = true) String collectionName,
            @RequestParam(value = "objectId", required = true) Integer objectId,
            Principal principal
    ){
        return collectionService.removeObjectToCollection(collectionName, objectId, principal);
    }
}
