package com.spotlightsarajevo.modules.category.service;

import com.spotlightsarajevo.modules.category.api.dto.EventCategoryModel;
import com.spotlightsarajevo.modules.category.api.dto.SpotCategoryModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * CategoryService interface defines methods for retrieving category-related data.
 */
public interface CategoryService {
    /**
     * Retrieves a list of all spot categories.
     *
     * @return ResponseEntity containing a list of SpotCategoryModel
     */
    ResponseEntity<List<SpotCategoryModel>> findAllSpotCategories();

    /**
     * Retrieves a list of all event categories.
     *
     * @return ResponseEntity containing a list of EventCategoryModel
     */
    ResponseEntity<List<EventCategoryModel>> findAllEventCategories();
}
