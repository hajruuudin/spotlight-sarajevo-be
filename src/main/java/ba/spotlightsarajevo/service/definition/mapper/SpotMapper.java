package ba.spotlightsarajevo.service.definition.mapper;

import ba.spotlightsarajevo.dao.entities.SpotEntity;
import ba.spotlightsarajevo.dao.entities.TagEntity;
import ba.spotlightsarajevo.dao.models.spot.SpotCreate;
import ba.spotlightsarajevo.dao.models.spot.SpotModel;
import ba.spotlightsarajevo.dao.models.spot.SpotShorthand;
import ba.spotlightsarajevo.dao.models.tag.TagModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpotMapper {
    SpotEntity dtoToEntity(SpotCreate spotCreate);
    SpotModel entityToDto(SpotEntity spotEntity);
    SpotShorthand entityToShorthandDto(SpotEntity spotEntity);
    List<SpotModel> entitiesToDtos(List<SpotEntity> entities);
}
