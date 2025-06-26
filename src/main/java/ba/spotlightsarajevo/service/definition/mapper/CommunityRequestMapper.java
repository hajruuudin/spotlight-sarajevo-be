package ba.spotlightsarajevo.service.definition.mapper;

import ba.spotlightsarajevo.dao.entities.CommunityRequestEntity;
import ba.spotlightsarajevo.dao.models.communityrequest.CommunityRequestModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommunityRequestMapper {
    CommunityRequestModel entityToDto(CommunityRequestEntity entity);
}
