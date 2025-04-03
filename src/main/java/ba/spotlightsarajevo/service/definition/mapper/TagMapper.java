package ba.spotlightsarajevo.service.definition.mapper;

import ba.spotlightsarajevo.dao.entities.TagEntity;
import ba.spotlightsarajevo.dao.models.tag.TagModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
    List<TagModel> entitiesToDtos(List<TagEntity> entities);
}
