package ba.spotlightsarajevo.rest;

import ba.spotlightsarajevo.dao.entities.MediaStoreEntity;
import ba.spotlightsarajevo.dao.models.mediastore.MediaStoreCreate;
import ba.spotlightsarajevo.service.definition.service.MediaStoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Media Store", description = "Media Store API")
@RequestMapping(value = "/media")
@AllArgsConstructor
@RestController
public class MediaStoreRestController {
    MediaStoreService mediaStoreService;

    @Operation(description = "Add a media to the database")
    @PostMapping
    public ResponseEntity<MediaStoreEntity> create(@RequestBody MediaStoreCreate request){
        return  mediaStoreService.create(request);
    }
}
