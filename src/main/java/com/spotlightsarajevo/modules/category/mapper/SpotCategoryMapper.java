package com.spotlightsarajevo.modules.category.mapper;

import com.spotlightsarajevo.modules.category.api.dto.SpotCategoryModel;
import com.spotlightsarajevo.modules.category.domain.entity.SpotCategoryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpotCategoryMapper {
    List<SpotCategoryModel> entitiesToDtos(List<SpotCategoryEntity> entities);
}
