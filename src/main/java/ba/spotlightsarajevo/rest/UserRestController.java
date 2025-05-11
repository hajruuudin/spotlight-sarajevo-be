package ba.spotlightsarajevo.rest;

import ba.spotlightsarajevo.dao.models.user.LoggedUserModel;
import ba.spotlightsarajevo.service.definition.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Tag(name = "User", description = "User API")
@RequestMapping(value = "/user")
@AllArgsConstructor
@RestController
public class UserRestController {
    UserService userService;

    @Operation(description = "Get a users profile based on the current authentication state")
    @GetMapping(value = "profile")
    public ResponseEntity<LoggedUserModel> getUserProfilePrinciple(Principal principal){
        return this.userService.getUserProfilePrinciple(principal);
    }
}
