package com.spotlightsarajevo.rest;

import com.spotlightsarajevo.dao.dto.userauth.*;
import com.spotlightsarajevo.service.definition.AuthService;
import com.spotlightsarajevo.utils.exceptions.AuthExceptions;
import com.spotlightsarajevo.utils.helpers.CommonConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

@RestController
@Tag(name = "authentication", description = "Authentication API Endpoints")
@AllArgsConstructor
@RequestMapping(value = "/auth")
public class AuthRESTController {
    AuthService authService;

    @Operation(description = "Register the users Google Credentials into the session")
    @PostMapping(value = "/google/register")
    public ResponseEntity<Map<String, Object>> storeGoogleCredentials(@RequestBody Map<String, String> payload, HttpSession session) throws GeneralSecurityException, IOException {
        return authService.storeGoogleCredentials(payload, session);
    }

    @Operation(description = "Register the users personal Credentials into the session")
    @PostMapping(value = "/system/register")
    public ResponseEntity<Map<String, Object>> storeSystemCredentials(@RequestBody SystemUserModel payload, HttpSession session){
        return authService.storeSystemCredentials(payload, session);
    }

    @Operation(description = "Register a user with their respective Credentials along with the survey data")
    @PostMapping(value = "/register")
    public ResponseEntity<UserModel> register(@RequestBody PreferencesModel request, HttpSession session){
        if(session.getAttribute(CommonConstants.GOOGLE_USER_INFO_SESSION_KEY) != null){
            GoogleUserModel googleData = (GoogleUserModel) session.getAttribute(CommonConstants.GOOGLE_USER_INFO_SESSION_KEY);

            return authService.registerByGoogle(googleData, request);
        } else if (session.getAttribute(CommonConstants.SYSTEM_USER_INFO_SESSION_KEY) != null){
            SystemUserModel systemData = (SystemUserModel) session.getAttribute(CommonConstants.SYSTEM_USER_INFO_SESSION_KEY);

            return authService.registerBySystem(systemData, request);
        } else {
            System.out.println(session.getAttribute(CommonConstants.SYSTEM_USER_INFO_SESSION_KEY));
            System.out.println(session.getAttribute(CommonConstants.GOOGLE_USER_INFO_SESSION_KEY));
            throw new AuthExceptions.AuthenticationEndpointException("Non-identifiable registration method");
        }
    }

    @Operation(description = "Login a user using Google Credentials")
    @PostMapping(value = "/login/google")
    public ResponseEntity<Map<String, Object>> loginGoogle(
            @RequestBody Map<String, String> payload, HttpServletResponse servletResponse) throws GeneralSecurityException, IOException {
        return authService.loginGoogle(payload, servletResponse);
    }

    @Operation(description = "Login a user with System Credentials")
    @PostMapping(value = "/login/system")
    public ResponseEntity<Map<String, Object>> loginSystem(
            @RequestBody SystemLoginModel request, HttpServletResponse servletResponse){
        return authService.loginSystem(request, servletResponse);
    }

    @Operation(description = "Log a user out of his sessions")
    @PostMapping(value = "/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpServletResponse response) {
        return authService.logout(response);
    }
}
