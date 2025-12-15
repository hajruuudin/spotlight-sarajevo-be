package com.spotlightsarajevo.modules.collection.api;

import com.spotlightsarajevo.modules.collection.api.dto.*;
import com.spotlightsarajevo.modules.collection.service.CollectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@Tag(name = "collection", description = "Collection API Routes")
@AllArgsConstructor
@RequestMapping(value = "/collection")
public class CollectionRESTController {
    CollectionService collectionService;

    @GetMapping(value = "/all")
    @Operation(description = "Get a users collections from the database, only the references for the objects")
    public ResponseEntity<List<CollectionModel>> findUserCollections(
            Principal principal
    ){
        return collectionService.findUserCollections(principal);
    }

    @PostMapping
    @Operation(description = "Create a new collection for the user, either spot or event")
    public ResponseEntity<CollectionModel> addCollection(
            @RequestBody CollectionCreateModel request,
            Principal principal
    ){
        return collectionService.addCollection(request, principal);
    }

    @PutMapping
    @Operation(description = "Update the name or the description of a collection")
    public ResponseEntity<CollectionModel> updateCollection(
            @RequestBody CollectionUpdateModel request,
            Principal principal
            ){
        return collectionService.updateCollection(request, principal);
    }

    @DeleteMapping(value = "/delete/{collectionId}")
    @Operation(description = "Delete a collection from the database")
    public ResponseEntity<CollectionModel> deleteCollection(
            @PathVariable Integer collectionId,
            Principal principal
    ){
        return collectionService.deleteCollection(collectionId, principal);
    }

    @GetMapping(value = "/{collectionId}/items")
    @Operation(description = "Find items for a specific collection")
    public ResponseEntity<CollectionItemsModel> findCollectionItems(
            @PathVariable Integer collectionId,
            Principal principal
    ){
        return collectionService.findCollectionItems(collectionId, principal);
    }

    @PostMapping(value = "/add-item")
    @Operation(description = "Add an item to a specified collection")
    public ResponseEntity<CollectionItemsModel> addItemToCollection(
            @RequestBody CollectionAddItemModel request,
            Principal principal
    ){
        return collectionService.addItemToCollection(request, principal);
    }

    @DeleteMapping(value = "/remove-item")
    @Operation(description = "Remove an item from a collection")
    public ResponseEntity<CollectionItemsModel> removeItemFromCollection(
            @RequestParam Integer collectionId,
            @RequestParam Integer itemId,
            @RequestParam String collectionType,
            Principal principal
    ){
        return collectionService.removeItemFromCollection(collectionId, itemId, collectionType, principal);
    }

}
