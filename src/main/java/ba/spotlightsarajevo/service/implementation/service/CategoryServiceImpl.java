package ba.spotlightsarajevo.service.implementation.service;

import ba.spotlightsarajevo.dao.CategoryDAO;
import ba.spotlightsarajevo.dao.entities.CategoryEntity;
import ba.spotlightsarajevo.dao.entities.TagEntity;
import ba.spotlightsarajevo.dao.models.category.CategoryModel;
import ba.spotlightsarajevo.dao.models.tag.TagModel;
import ba.spotlightsarajevo.service.definition.mapper.CategoryMapper;
import ba.spotlightsarajevo.service.definition.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    CategoryDAO categoryDAO;
    CategoryMapper categoryMapper;

    @Override
    public ResponseEntity<Page<CategoryModel>> findAll(PageRequest request) {
        Page<CategoryEntity> pagedCategoryResponse = categoryDAO.findAll(request);

        List<CategoryModel> categoryModelList = categoryMapper.entitiesToDtos(pagedCategoryResponse.getContent());

        Page<CategoryModel> categoryResponse = new PageImpl<>(
                categoryModelList,
                request,
                pagedCategoryResponse.getTotalElements()
        );

        return ResponseEntity.ok(categoryResponse);
    }
}
