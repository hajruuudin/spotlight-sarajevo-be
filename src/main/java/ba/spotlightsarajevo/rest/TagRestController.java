package ba.spotlightsarajevo.rest;

import ba.spotlightsarajevo.dao.models.tag.TagModel;
import ba.spotlightsarajevo.service.definition.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Tag", description = "Tag API")
@RequestMapping(value = "/tag")
@AllArgsConstructor
@RestController
public class TagRestController {
    TagService tagService;

    @Operation(description = "Find all tags from the database")
    @GetMapping
    public ResponseEntity<Page<TagModel>> findAll(){
        return tagService.findAll(PageRequest.of(0, 100));
    }
}
