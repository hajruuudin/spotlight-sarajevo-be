package ba.spotlightsarajevo.service.definition.mapper;

import ba.spotlightsarajevo.dao.entities.SpotEntity;
import ba.spotlightsarajevo.dao.models.spot.SpotCreate;
import ba.spotlightsarajevo.dao.models.spot.SpotModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpotMapper {
    SpotEntity dtoToEntity(SpotCreate spotCreate);
    SpotModel entityToDto(SpotEntity spotEntity);
}
