package com.spotlightsarajevo.modules.event.mapper;

import com.spotlightsarajevo.modules.event.api.dto.EventOrganiserModel;
import com.spotlightsarajevo.modules.event.domain.entity.EventOrganiserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventOrganiserMapper {
    EventOrganiserModel entityToDto(EventOrganiserEntity entity);
}
