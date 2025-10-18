package com.spotlightsarajevo.service.implementation;

import com.spotlightsarajevo.dao.dto.category.EventCategoryModel;
import com.spotlightsarajevo.dao.dto.category.SpotCategoryModel;
import com.spotlightsarajevo.service.definition.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Override
    public ResponseEntity<List<SpotCategoryModel>> findAllSpotCategories() {
        return null;
    }

    @Override
    public ResponseEntity<List<EventCategoryModel>> findAllEventCategories() {
        return null;
    }
}
