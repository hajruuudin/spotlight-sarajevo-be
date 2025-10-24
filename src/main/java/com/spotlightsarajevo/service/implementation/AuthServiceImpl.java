package com.spotlightsarajevo.service.implementation;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.spotlightsarajevo.dao.access.UserDAO;
import com.spotlightsarajevo.dao.dto.userauth.*;
import com.spotlightsarajevo.dao.entity.UserEntity;
import com.spotlightsarajevo.service.definition.AuthService;
import com.spotlightsarajevo.utils.exceptions.AuthExceptions;
import com.spotlightsarajevo.utils.helpers.Constants;
import com.spotlightsarajevo.utils.helpers.UtilsFunctions;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    UserDAO userDAO;

    @Override
    public ResponseEntity<Map<String, Object>> storeGoogleCredentials(Map<String, String> payload, HttpSession session) throws GeneralSecurityException, IOException {
        String idTokenString = payload.get("idToken");

        if(idTokenString == null){
            throw new AuthExceptions.GoogleRegisterException("Id token not found");
        } else {
            GoogleIdToken finalIdToken = UtilsFunctions.verifyGoogleToken(idTokenString);

            if(finalIdToken != null){
                GoogleIdToken.Payload tokenPayload = finalIdToken.getPayload();

                // Extract user information
                String googleId = tokenPayload.getSubject();
                String email = tokenPayload.getEmail();
                String firstName = (String) tokenPayload.get("given_name");
                String lastName = (String) tokenPayload.get("family_name");

                UserEntity checkUserEntity = userDAO.findByGoogleId(googleId);

                if(checkUserEntity != null){
                    return ResponseEntity.status(400).body(Map.of("message", "An account is already registered to that email. Please log in!"));
                }

                // Store this information in the session temporarily
                GoogleUserModel googleUserInfo = new GoogleUserModel();
                googleUserInfo.setGoogleId(googleId);
                googleUserInfo.setEmail(email);
                googleUserInfo.setFirstName(firstName);
                googleUserInfo.setLastName(lastName);

                session.setAttribute(Constants.GOOGLE_USER_INFO_SESSION_KEY, googleUserInfo);

                return ResponseEntity.status(200).body(Map.of("message", "Successfully stored Google data to session", "firstName", googleUserInfo.getFirstName()));
            } else {
                throw new AuthExceptions.GoogleRegisterException("Google could not verify Your ID token!");
            }
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> storeSystemCredentials(SystemUserModel payload, HttpSession session) {
        return null;
    }

    @Override
    public ResponseEntity<UserModel> registerByGoogle(GoogleUserModel googleData, PreferencesModel preferencesModel) {
        return null;
    }

    @Override
    public ResponseEntity<UserModel> registerBySystem(SystemUserModel systemData, PreferencesModel preferencesModel) {
        return null;
    }

    @Override
    public ResponseEntity<Map<String, Object>> loginGoogle(Map<String, String> payload, HttpServletResponse servletResponse) throws GeneralSecurityException, IOException {
        return null;
    }

    @Override
    public ResponseEntity<Map<String, Object>> loginSystem(SystemLoginModel request, HttpServletResponse servletResponse) {
        return null;
    }

    @Override
    public ResponseEntity<Map<String, Object>> checkEmail(String email) {
        return null;
    }

    @Override
    public ResponseEntity<Map<String, Object>> logout(HttpServletResponse response) {
        return null;
    }


}
