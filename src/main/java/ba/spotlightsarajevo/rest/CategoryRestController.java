package ba.spotlightsarajevo.rest;

import ba.spotlightsarajevo.dao.models.category.CategoryModel;
import ba.spotlightsarajevo.service.definition.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Category", description = "Category API")
@RequestMapping(value = "/category")
@AllArgsConstructor
@RestController
public class CategoryRestController {
    CategoryService categoryService;

    // Routes: get all, get singular
    @Operation(description = "Find all categories from the database")
    @GetMapping
    public ResponseEntity<Page<CategoryModel>> findAll(){
        return categoryService.findAll(PageRequest.of(0, 30));
    }
}
