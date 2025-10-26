package com.spotlightsarajevo.rest;

import com.spotlightsarajevo.dao.dto.category.EventCategoryModel;
import com.spotlightsarajevo.dao.dto.category.SpotCategoryModel;
import com.spotlightsarajevo.service.definition.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "category", description = "Category API")
@AllArgsConstructor
@RequestMapping(value = "/category")
public class CategoryRESTController {
    CategoryService categoryService;

    @GetMapping(value = "/allSpot")
    @Operation(description = "Get all spot categories from the system")
    public ResponseEntity<List<SpotCategoryModel>> findAllSpot(){
        return this.categoryService.findAllSpotCategories();
    }

    @GetMapping(value = "/allEvent")
    @Operation(description = "Get all event categories from the system")
    public ResponseEntity<List<EventCategoryModel>> findAllEvent(){
        return this.categoryService.findAllEventCategories();
    }
}
