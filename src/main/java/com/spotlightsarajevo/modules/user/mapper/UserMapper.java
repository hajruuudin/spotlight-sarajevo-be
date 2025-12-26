package com.spotlightsarajevo.modules.user.mapper;

import com.spotlightsarajevo.modules.user.api.dto.LoggedUserModel;
import com.spotlightsarajevo.modules.user.api.dto.UserModel;
import com.spotlightsarajevo.modules.user.domain.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserModel entityToDto(UserEntity entity);
    LoggedUserModel entityToLoggedDto(UserEntity entity);
}
