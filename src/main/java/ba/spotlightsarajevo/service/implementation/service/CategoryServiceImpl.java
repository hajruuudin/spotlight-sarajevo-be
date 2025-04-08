package ba.spotlightsarajevo.service.implementation.service;

import ba.spotlightsarajevo.dao.CategoryDAO;
import ba.spotlightsarajevo.dao.entities.CategoryEntity;
import ba.spotlightsarajevo.dao.models.category.CategoryModel;
import ba.spotlightsarajevo.service.definition.mapper.CategoryMapper;
import ba.spotlightsarajevo.service.definition.service.CategoryService;
import ba.spotlightsarajevo.utils.ListEntityResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    CategoryDAO categoryDAO;
    CategoryMapper categoryMapper;

    @Override
    public ListEntityResponse<CategoryModel> findAll() {
        List<CategoryEntity> entities = categoryDAO.findAll();
        List<CategoryModel> models = categoryMapper.entitiesToDtos(entities);

        return new ListEntityResponse<>(
                "SUCCESS",
                "200",
                LocalDateTime.now(),
                models
        );
    }
}
