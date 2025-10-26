package com.spotlightsarajevo.service.mappers;

import com.spotlightsarajevo.dao.dto.userauth.UserModel;
import com.spotlightsarajevo.dao.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserModel entityToDto(UserEntity entity);
}
