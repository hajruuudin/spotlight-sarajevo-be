package ba.spotlightsarajevo.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Community Request", description = "Community Request API")
@RequestMapping(value = "/community-request")
@AllArgsConstructor
@RestController
public class CommunityRequestRestController {
    // find all, find by id, create, remove
}
