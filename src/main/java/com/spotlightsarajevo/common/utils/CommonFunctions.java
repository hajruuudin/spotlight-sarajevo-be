package com.spotlightsarajevo.common.utils;

import com.spotlightsarajevo.common.exceptions.AuthExceptions;
import com.spotlightsarajevo.modules.user.domain.UserDAO;
import com.spotlightsarajevo.modules.user.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CommonFunctions {
    UserDAO userDAO;

    public UserEntity getUserFromPrincipal(Principal principal){
        Optional<UserEntity> user = userDAO.findBySysEmailOrGoogleEmail(principal.getName());

        if(user.isPresent()){
            return user.get();
        } else {
            throw new AuthExceptions.AuthenticationUserNotFoundException("Principal User Not Found");
        }
    }
}
