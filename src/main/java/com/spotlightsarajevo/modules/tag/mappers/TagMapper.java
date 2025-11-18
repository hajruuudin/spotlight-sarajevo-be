package com.spotlightsarajevo.modules.tag.mappers;

import com.spotlightsarajevo.modules.tag.api.dto.TagModel;
import com.spotlightsarajevo.modules.tag.domain.entity.TagEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
    List<TagModel> entitiesToDtos(List<TagEntity> tagEntities);
}
