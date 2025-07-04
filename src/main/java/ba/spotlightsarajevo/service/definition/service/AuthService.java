package ba.spotlightsarajevo.service.definition.service;

import ba.spotlightsarajevo.dao.models.intermediate.GoogleUserModel;
import ba.spotlightsarajevo.dao.models.intermediate.PreferencesModel;
import ba.spotlightsarajevo.dao.models.intermediate.SystemUserModel;
import ba.spotlightsarajevo.dao.models.user.SystemLogin;
import ba.spotlightsarajevo.dao.models.user.UserModel;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

public interface AuthService {
    ResponseEntity<Map<String, Object>> storeGoogleCredentials(Map<String, String> payload, HttpSession session) throws GeneralSecurityException, IOException;
    ResponseEntity<Map<String, Object>> storeSystemCredentials(SystemUserModel payload, HttpSession session);
    ResponseEntity<UserModel> registerByGoogle(GoogleUserModel googleData, PreferencesModel preferences);
    ResponseEntity<UserModel> registerBySystem(SystemUserModel systemData, PreferencesModel preferencesModel);
    ResponseEntity<Map<String, Object>> loginGoogle(Map<String, String> payload, HttpServletResponse servletResponse) throws GeneralSecurityException, IOException;
    ResponseEntity<Map<String, Object>> loginSystem(SystemLogin request, HttpServletResponse servletResponse);
    ResponseEntity<Map<String, Object>> checkEmail(String email);
    ResponseEntity<Map<String, Object>> logout(HttpServletResponse response);
}
