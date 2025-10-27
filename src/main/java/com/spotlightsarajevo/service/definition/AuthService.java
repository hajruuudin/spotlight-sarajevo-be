package com.spotlightsarajevo.service.definition;

import com.spotlightsarajevo.dao.dto.userauth.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import com.spotlightsarajevo.utils.exceptions.AuthExceptions;
import com.spotlightsarajevo.utils.exceptions.AuthExceptions.SystemLoginException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

public interface AuthService {
    /**
     * Service method to store google credentials within a session. This method stores the users main credentials
     * (being email, first and last name, created username and google id, all fields required by the {@link GoogleUserModel}).
     * The data is stored in a temporary session on the backend, when it is later pulled in the register methods. The method first
     * verifies if the token is valid, after which is attempts to retrieve the Google Data from Googles servers. If any of the data
     * is not present, the mentioned exception will be thrown. Upon success, the data should be stored within a newly made session for the user.
     *
     * @param payload The payload only consists of a {@link String} labeled <b>idToken</b> which is used to identify the users Google account.
     *                This is received from the frontend in a String format.
     * @param session The HTTP session of the user used to add the data temporarily to the backend.
     *
     * @throws AuthExceptions.GoogleRegisterException in case the idToken is null, is not present, is invalid or any other potential mishap with Googles GSI api.
     * @see com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
     */
    ResponseEntity<Map<String, Object>> storeGoogleCredentials(Map<String, String> payload, HttpSession session) throws GeneralSecurityException, IOException;

    /**
     * Service method to store system credentials within a session. This method stores the users main credentials
     * (being email, first and last name, generated username and hashed password, all field required by the {@link SystemUserModel}).
     * The data is stored in a temporary session when it is later pulled in the register methods for system users. The method
     * checks if the data is present (if not, throws the exception). Afterward, it hashes the password and stores the user credentials into the session.
     *
     * @param payload The data sent from the form on the frontend, essentially a {@link SystemUserModel}
     * @param session The HTTP session of the user to add the data temporarily to the backend.
     *
     * @throws SystemLoginException in case anything is missing, data does not get added or unexpected errors occur within the method.
     * @see org.springframework.security.crypto.bcrypt.BCrypt
     */
    ResponseEntity<Map<String, Object>> storeSystemCredentials(SystemUserModel payload, HttpSession session);

    ResponseEntity<UserModel> registerByGoogle(GoogleUserModel googleData, PreferencesModel preferencesModel);
    ResponseEntity<UserModel> registerBySystem(SystemUserModel systemData, PreferencesModel preferencesModel);
    ResponseEntity<Map<String, Object>> loginGoogle(Map<String, String> payload, HttpServletResponse servletResponse) throws GeneralSecurityException, IOException;
    ResponseEntity<Map<String, Object>> loginSystem(SystemLoginModel request, HttpServletResponse servletResponse);
    ResponseEntity<Map<String, Object>> logout(HttpServletResponse response);
}
