package ba.spotlightsarajevo.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Collection", description = "Collection API")
@RequestMapping(value = "/collection")
@AllArgsConstructor
@RestController
public class CollectionRestController {
    // Routes: get all collections for a user, add a collection for a user,
    // delete a collection for a user, add event/spot to a collection, remove event/spot from a collection
    // get all events/spots inside a collection
}
