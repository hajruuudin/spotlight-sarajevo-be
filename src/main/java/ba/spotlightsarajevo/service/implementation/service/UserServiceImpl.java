package ba.spotlightsarajevo.service.implementation.service;

import ba.spotlightsarajevo.dao.UserDAO;
import ba.spotlightsarajevo.dao.entities.UserEntity;
import ba.spotlightsarajevo.dao.models.user.LoggedUserModel;
import ba.spotlightsarajevo.service.definition.mapper.UserMapper;
import ba.spotlightsarajevo.service.definition.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    UserDAO userDAO;
    UserMapper userMapper;

    @Override
    public ResponseEntity<LoggedUserModel> getUserProfilePrinciple(Principal principal) {
        if(principal == null){
            return ResponseEntity.status(401).body(null); // User is unauthorized
        }

        UserEntity userEntity = this.userDAO.findByEmail(principal.getName());

        LoggedUserModel loggedUserModel = userMapper.entityToLoggedDto(userEntity);

        // Conditionally, set either the Google email or system email
        if(userEntity.getEmail() == null){
            loggedUserModel.setEmail(userEntity.getGoogleEmail());
            loggedUserModel.setImageUrl(userEntity.getGooglePictureUrl());
        } else {
            loggedUserModel.setEmail(userEntity.getEmail());
            // This line will be replaced with the lookup service
        };

        return ResponseEntity.ok(loggedUserModel);
    }
}
