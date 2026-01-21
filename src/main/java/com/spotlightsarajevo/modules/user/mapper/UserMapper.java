package com.spotlightsarajevo.modules.user.mapper;

import com.spotlightsarajevo.modules.user.api.dto.LoggedUserModel;
import com.spotlightsarajevo.modules.user.api.dto.UserModel;
import com.spotlightsarajevo.modules.user.domain.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserModel entityToDto(UserEntity entity);
    LoggedUserModel entityToLoggedDto(UserEntity entity);
}
