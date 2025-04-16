package ba.spotlightsarajevo.service.definition.mapper;

import ba.spotlightsarajevo.dao.entities.UserEntity;
import ba.spotlightsarajevo.dao.models.user.UserModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserModel entityToDto(UserEntity entity);
    UserEntity dtoToEntity(UserModel model);
}
