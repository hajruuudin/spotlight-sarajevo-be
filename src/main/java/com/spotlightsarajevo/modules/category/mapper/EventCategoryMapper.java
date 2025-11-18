package com.spotlightsarajevo.modules.category.mapper;

import com.spotlightsarajevo.modules.category.api.dto.EventCategoryModel;
import com.spotlightsarajevo.modules.category.domain.entity.EventCategoryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventCategoryMapper {
    List<EventCategoryModel> entitiesToDtos(List<EventCategoryEntity> entity);
}
