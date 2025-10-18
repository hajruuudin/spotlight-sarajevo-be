package com.spotlightsarajevo.service.definition;

import com.spotlightsarajevo.dao.dto.category.EventCategoryModel;
import com.spotlightsarajevo.dao.dto.category.SpotCategoryModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    ResponseEntity<List<SpotCategoryModel>> findAllSpotCategories();
    ResponseEntity<List<EventCategoryModel>> findAllEventCategories();
}
