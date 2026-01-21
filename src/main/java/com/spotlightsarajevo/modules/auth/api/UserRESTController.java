package com.spotlightsarajevo.modules.auth.api;

import com.spotlightsarajevo.modules.auth.api.dto.UserInfoModel;
import com.spotlightsarajevo.modules.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Tag(name = "user", description = "User API Endpoints")
@AllArgsConstructor
@RequestMapping(value = "/user")
public class UserRESTController {
    UserService userService;

    @Operation(description = "Get detailed information about the logged in user")
    @GetMapping(value = "/info")
    public ResponseEntity<UserInfoModel> findUserInfo(Principal principal){
        return userService.findUserInfo(principal);
    }
}
