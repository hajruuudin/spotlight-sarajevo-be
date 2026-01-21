package com.spotlightsarajevo.modules.auth.service;

import com.spotlightsarajevo.modules.auth.api.dto.UserInfoModel;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

/**
 * Service interface for user-related operations. Does not include authentication methods.
 */
public interface UserService {
    ResponseEntity<UserInfoModel> findUserInfo(Principal principal);
}
