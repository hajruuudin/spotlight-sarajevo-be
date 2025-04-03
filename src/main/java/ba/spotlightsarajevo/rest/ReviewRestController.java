package ba.spotlightsarajevo.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Review", description = "Review API")
@RequestMapping(value = "/review")
@AllArgsConstructor
@RestController
public class ReviewRestController {
    // create, delete, get all for an event id paginated
}
