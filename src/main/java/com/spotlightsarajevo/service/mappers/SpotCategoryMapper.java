package com.spotlightsarajevo.service.mappers;

import com.spotlightsarajevo.dao.dto.category.SpotCategoryModel;
import com.spotlightsarajevo.dao.entity.SpotCategoryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpotCategoryMapper {
    List<SpotCategoryModel> entitiesToDtos(List<SpotCategoryEntity> entities);
}
