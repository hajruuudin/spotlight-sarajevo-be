package ba.spotlightsarajevo.rest;

import ba.spotlightsarajevo.dao.models.tag.TagModel;
import ba.spotlightsarajevo.service.definition.service.TagService;
import ba.spotlightsarajevo.utils.ListEntityResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Tag", description = "Tag API")
@RequestMapping(value = "/tag")
@AllArgsConstructor
@RestController
public class TagRestController {
    TagService tagService;

    @Operation(description = "Find all tags from the database")
    @GetMapping
    public ListEntityResponse<TagModel> findAll(){
        return tagService.findAll();
    }
}
