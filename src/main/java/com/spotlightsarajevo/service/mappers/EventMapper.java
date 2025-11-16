package com.spotlightsarajevo.service.mappers;

import com.spotlightsarajevo.dao.dto.event.EventShorthandModel;
import com.spotlightsarajevo.dao.entity.EventEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    List<EventShorthandModel> entitiesToShorthandDtos(List<EventEntity> entities);
}
