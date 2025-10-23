package com.spotlightsarajevo.service.mappers;

import com.spotlightsarajevo.dao.dto.category.EventCategoryModel;
import com.spotlightsarajevo.dao.entity.EventCategoryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventCategoryMapper {
    List<EventCategoryModel> entitiesToDtos(List<EventCategoryEntity> entity);
}
