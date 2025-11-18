package com.spotlightsarajevo.modules.category.service;

import com.spotlightsarajevo.modules.category.api.dto.EventCategoryModel;
import com.spotlightsarajevo.modules.category.api.dto.SpotCategoryModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    ResponseEntity<List<SpotCategoryModel>> findAllSpotCategories();
    ResponseEntity<List<EventCategoryModel>> findAllEventCategories();
}
