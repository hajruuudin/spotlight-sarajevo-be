package ba.spotlightsarajevo.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Media Store", description = "Media Store API")
@RequestMapping(value = "/media")
@AllArgsConstructor
@RestController
public class MediaStoreRestController {
    // create, delete, find by object id and object type
}
