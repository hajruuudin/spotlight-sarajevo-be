package com.spotlightsarajevo.service.mappers;

import com.spotlightsarajevo.dao.dto.tag.TagModel;
import com.spotlightsarajevo.dao.entity.TagEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
    List<TagModel> entitiesToDtos(List<TagEntity> tagEntities);
}
