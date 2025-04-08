package ba.spotlightsarajevo.service.definition.service;

import ba.spotlightsarajevo.dao.models.category.CategoryModel;
import ba.spotlightsarajevo.utils.ListEntityResponse;

public interface CategoryService {
    ListEntityResponse<CategoryModel> findAll();
}
