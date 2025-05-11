package ba.spotlightsarajevo.rest;

import ba.spotlightsarajevo.dao.models.collection.CollectionWithItemsModel;
import ba.spotlightsarajevo.service.definition.service.CollectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return this.collectionService.getCollectionByName(collectionName, principal);
    }
}
