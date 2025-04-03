package ba.spotlightsarajevo.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "User API")
@RequestMapping(value = "/user")
@AllArgsConstructor
@RestController
public class UserRestController {
    // find By Id, update
}
