package com.spotlightsarajevo.modules.auth.utils;

import com.spotlightsarajevo.modules.auth.domain.entity.UserEntity;
import com.spotlightsarajevo.modules.category.facades.UserCategoryFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UserInfoUtils {
    private UserCategoryFacade userCategoryFacade;

    public void setFavoriteCategories(UserEntity entity, List<Integer> spotIds, List<Integer> eventIds){
        entity.setFavoriteSpotCategories(
                userCategoryFacade.getUserFavoriteSpotCategories(
                       spotIds
                )
        );

        entity.setFavoriteEventCategories(
                userCategoryFacade.getUserFavoriteEventCategories(
                        eventIds
                )
        );
    }
}
