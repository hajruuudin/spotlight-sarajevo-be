package com.spotlightsarajevo.modules.event.mapper;

import com.spotlightsarajevo.modules.event.api.dto.EventModel;
import com.spotlightsarajevo.modules.event.api.dto.EventOverviewModel;
import com.spotlightsarajevo.modules.event.api.dto.EventShorthandModel;
import com.spotlightsarajevo.modules.event.api.dto.EventUpdateModel;
import com.spotlightsarajevo.modules.event.domain.entity.EventEntity;
import com.spotlightsarajevo.modules.spot.api.dto.SpotUpdateModel;
import com.spotlightsarajevo.modules.spot.domain.entity.SpotEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {
    EventModel entityToDto(EventEntity entity);
    List<EventShorthandModel> entitiesToShorthandDtos(List<EventEntity> entities);
    EventOverviewModel entityToOverviewDto(EventEntity eventEntity);
    @Mapping(target = "thumbnailImage", ignore = true)
    void updateEntityFromDto(EventUpdateModel eventUpdateModel, @MappingTarget EventEntity eventEntity);
    EventShorthandModel entityToShorthandDto(EventEntity eventEntity);
}
