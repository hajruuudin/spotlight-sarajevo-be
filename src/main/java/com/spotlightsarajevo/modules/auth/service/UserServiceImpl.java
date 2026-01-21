package com.spotlightsarajevo.modules.auth.service;

import com.spotlightsarajevo.common.enums.ExceptionCodes;
import com.spotlightsarajevo.common.exceptions.AuthExceptions;
import com.spotlightsarajevo.modules.auth.api.dto.LoggedUserModel;
import com.spotlightsarajevo.modules.auth.api.dto.UserInfoModel;
import com.spotlightsarajevo.modules.auth.domain.*;
import com.spotlightsarajevo.modules.auth.domain.entity.UserEntity;
import com.spotlightsarajevo.modules.auth.domain.entity.UserFavouriteEventsEntity;
import com.spotlightsarajevo.modules.auth.domain.entity.UserFavouriteSpotsEntity;
import com.spotlightsarajevo.modules.auth.mapper.UserMapper;
import com.spotlightsarajevo.modules.auth.utils.UserInfoUtils;
import com.spotlightsarajevo.modules.category.api.dto.SpotCategoryModel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    UserDAO userDAO;
    UserAttendedEventsDAO userAttendedEventsDAO;
    UserVisitedSpotsDAO userVisitedSpotsDAO;
    UserFavouriteEventsDAO userFavouriteEventsDAO;
    UserFavouriteSpotsDAO userFavouriteSpotsDAO;
    UserMapper userMapper;
    UserInfoUtils utils;

    @Override
    public ResponseEntity<UserInfoModel> findUserInfo(Principal principal) {
        if(principal == null){
            return ResponseEntity.status(200).body(null);
        }

        Optional<UserEntity> user = userDAO.findBySysEmailOrGoogleEmail(principal.getName());

        if(user.isPresent()){
            List<UserFavouriteSpotsEntity> favouriteSpots = userFavouriteSpotsDAO.findAllByUserId(user.get().getId());
            List<UserFavouriteEventsEntity> favouriteEvents = userFavouriteEventsDAO.findAllByUserId(user.get().getId());

            List<Integer> favouriteSpotCategoryIds = favouriteSpots.stream()
                    .map(UserFavouriteSpotsEntity::getSpotId)
                    .toList();

            List<Integer> favouriteEventCategoryIds = favouriteEvents.stream()
                    .map(UserFavouriteEventsEntity::getEventId)
                    .toList();

            utils.setFavoriteCategories(user.get(), favouriteSpotCategoryIds, favouriteEventCategoryIds);

            user.get().setVisitedSpotsCount(
                    userVisitedSpotsDAO.countAllByUserId(user.get().getId())
            );

            user.get().setAttendedEventsCount(
                    userAttendedEventsDAO.countAllByUserId(user.get().getId())
            );

            if(user.get().getSysEmail() == null){
                return ResponseEntity.status(200).body(
                        userMapper.infoEntityToGoogleDto(user.get())
                );
            } else {
                return ResponseEntity.status(200).body(
                        userMapper.infoEntityToSystemDto(user.get())
                );
            }
        } else {
            throw new AuthExceptions.AuthenticationUserNotFoundException(ExceptionCodes.AuthExceptionCodes.AUTH_USER_NOT_FOUND.toString());
        }
    }
}
