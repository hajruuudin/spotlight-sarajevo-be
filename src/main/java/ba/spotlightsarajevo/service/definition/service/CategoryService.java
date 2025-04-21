package ba.spotlightsarajevo.service.definition.service;

import ba.spotlightsarajevo.dao.models.category.CategoryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    ResponseEntity<Page<CategoryModel>> findAll(PageRequest request);
}
