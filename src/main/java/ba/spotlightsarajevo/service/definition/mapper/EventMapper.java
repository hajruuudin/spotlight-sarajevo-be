package ba.spotlightsarajevo.service.definition.mapper;

import ba.spotlightsarajevo.dao.entities.EventEntity;
import ba.spotlightsarajevo.dao.entities.SpotEntity;
import ba.spotlightsarajevo.dao.models.event.EventCreate;
import ba.spotlightsarajevo.dao.models.event.EventModel;
import ba.spotlightsarajevo.dao.models.event.EventShorthand;
import ba.spotlightsarajevo.dao.models.spot.SpotShorthand;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventEntity dtoToEntity(EventCreate eventCreate);
    EventModel entityToDto(EventEntity eventEntity);
    EventShorthand entityToShorthandDto(EventEntity eventEntity);
    List<EventModel> entitiesToDtos(List<EventEntity> entities);

    @IterableMapping(elementTargetType = EventShorthand.class)
    List<EventShorthand> entitiesToShorthandDtos(List<EventEntity> entities);
}