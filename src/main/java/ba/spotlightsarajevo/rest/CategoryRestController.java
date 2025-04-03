package ba.spotlightsarajevo.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Category", description = "Category API")
@RequestMapping(value = "/category")
@AllArgsConstructor
@RestController
public class CategoryRestController {
    // Routes: get all, get singular
}
