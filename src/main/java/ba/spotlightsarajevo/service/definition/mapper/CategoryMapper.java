package ba.spotlightsarajevo.service.definition.mapper;

import ba.spotlightsarajevo.dao.entities.CategoryEntity;
import ba.spotlightsarajevo.dao.models.category.CategoryModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    List<CategoryModel> entitiesToDtos(List<CategoryEntity> entities);
}
