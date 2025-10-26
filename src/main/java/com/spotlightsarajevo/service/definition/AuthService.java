package com.spotlightsarajevo.service.definition;

import com.spotlightsarajevo.dao.dto.userauth.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.security.GeneralSecurityException;
import java.util.Map;

public interface AuthService {
    ResponseEntity<Map<String, Object>> storeGoogleCredentials(Map<String, String> payload, HttpSession session) throws GeneralSecurityException, IOException;
    ResponseEntity<Map<String, Object>> storeSystemCredentials(SystemUserModel payload, HttpSession session);
    ResponseEntity<UserModel> registerByGoogle(GoogleUserModel googleData, PreferencesModel preferencesModel);
    ResponseEntity<UserModel> registerBySystem(SystemUserModel systemData, PreferencesModel preferencesModel);
    ResponseEntity<Map<String, Object>> loginGoogle(Map<String, String> payload, HttpServletResponse servletResponse) throws GeneralSecurityException, IOException;
    ResponseEntity<Map<String, Object>> loginSystem(SystemLoginModel request, HttpServletResponse servletResponse);
    ResponseEntity<Map<String, Object>> logout(HttpServletResponse response);
}
