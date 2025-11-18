package com.spotlightsarajevo.modules.category.service;

import com.spotlightsarajevo.modules.category.domain.EventCategoryDAO;
import com.spotlightsarajevo.modules.category.domain.SpotCategoryDAO;
import com.spotlightsarajevo.modules.category.api.dto.EventCategoryModel;
import com.spotlightsarajevo.modules.category.api.dto.SpotCategoryModel;
import com.spotlightsarajevo.modules.category.domain.entity.EventCategoryEntity;
import com.spotlightsarajevo.modules.category.domain.entity.SpotCategoryEntity;
import com.spotlightsarajevo.modules.category.mapper.EventCategoryMapper;
import com.spotlightsarajevo.modules.category.mapper.SpotCategoryMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    SpotCategoryDAO spotCategoryDAO;
    EventCategoryDAO eventCategoryDAO;
    SpotCategoryMapper spotCategoryMapper;
    EventCategoryMapper eventCategoryMapper;

    @Override
    public ResponseEntity<List<SpotCategoryModel>> findAllSpotCategories() {
        try{
            List<SpotCategoryEntity> entities = spotCategoryDAO.findAll();
            List<SpotCategoryModel> models = spotCategoryMapper.entitiesToDtos(entities);

            return ResponseEntity.status(200).body(models);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public ResponseEntity<List<EventCategoryModel>> findAllEventCategories() {
        try{
            List<EventCategoryEntity> entities = eventCategoryDAO.findAll();
            List<EventCategoryModel> models = eventCategoryMapper.entitiesToDtos(entities);

            return ResponseEntity.status(200).body(models);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
