package ba.spotlightsarajevo.service.definition.mapper;

import ba.spotlightsarajevo.dao.entities.CollectionEntity;
import ba.spotlightsarajevo.dao.models.collection.CollectionModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CollectionMapper {
    CollectionModel entityToDto(CollectionEntity entity);
}
