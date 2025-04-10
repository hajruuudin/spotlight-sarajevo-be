package ba.spotlightsarajevo.service.definition.service;

import ba.spotlightsarajevo.dao.models.intermediate.GoogleUserModel;
import ba.spotlightsarajevo.dao.models.intermediate.PreferencesModel;
import ba.spotlightsarajevo.dao.models.intermediate.SystemUserModel;
import ba.spotlightsarajevo.dao.models.user.AuthenticatedUser;
import ba.spotlightsarajevo.dao.models.user.SystemLogin;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

public interface AuthService {
    ResponseEntity<?> storeGoogleCredentials(Map<String, String> payload, HttpSession session) throws GeneralSecurityException, IOException;
    ResponseEntity<?> storeSystemCredentials(SystemUserModel payload, HttpSession session);
    ResponseEntity<?> registerByGoogle(GoogleUserModel googleData, PreferencesModel preferences);
    ResponseEntity<?> registerBySystem(SystemUserModel systemData, PreferencesModel preferencesModel);
    ResponseEntity<?> loginGoogle(Map<String, String> payload, HttpServletResponse servletResponse) throws GeneralSecurityException, IOException;
    ResponseEntity<?> loginSystem(SystemLogin request, HttpServletResponse servletResponse);
    ResponseEntity<?> checkEmail(String email);
}
