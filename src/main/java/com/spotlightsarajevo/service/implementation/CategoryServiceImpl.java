package com.spotlightsarajevo.service.implementation;

import com.spotlightsarajevo.dao.access.EventCategoryDAO;
import com.spotlightsarajevo.dao.access.SpotCategoryDAO;
import com.spotlightsarajevo.dao.dto.category.EventCategoryModel;
import com.spotlightsarajevo.dao.dto.category.SpotCategoryModel;
import com.spotlightsarajevo.dao.entity.EventCategoryEntity;
import com.spotlightsarajevo.dao.entity.SpotCategoryEntity;
import com.spotlightsarajevo.service.definition.CategoryService;
import com.spotlightsarajevo.service.mappers.EventCategoryMapper;
import com.spotlightsarajevo.service.mappers.SpotCategoryMapper;
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
