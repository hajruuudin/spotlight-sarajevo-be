package ba.spotlightsarajevo.service.definition.mapper;

import ba.spotlightsarajevo.dao.entities.CollectionEntity;
import ba.spotlightsarajevo.dao.models.collection.CollectionModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CollectionMapper {
    CollectionModel entityToDto(CollectionEntity entity);
    List<CollectionModel> entitiesToDtos(List<CollectionEntity> entities);
}
