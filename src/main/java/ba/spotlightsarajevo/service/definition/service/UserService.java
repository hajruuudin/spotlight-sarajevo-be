package ba.spotlightsarajevo.service.definition.service;

import ba.spotlightsarajevo.dao.models.user.LoggedUserModel;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface UserService {
    ResponseEntity<LoggedUserModel> getUserProfilePrinciple(Principal principal);
}
