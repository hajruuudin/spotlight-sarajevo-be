package com.spotlightsarajevo.modules.event.mapper;

import com.spotlightsarajevo.modules.event.api.dto.EventShorthandModel;
import com.spotlightsarajevo.modules.event.domain.entity.EventEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    List<EventShorthandModel> entitiesToShorthandDtos(List<EventEntity> entities);
}
