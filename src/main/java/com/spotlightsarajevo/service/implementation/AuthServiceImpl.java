package com.spotlightsarajevo.service.implementation;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.spotlightsarajevo.dao.dto.userauth.*;
import com.spotlightsarajevo.service.definition.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    private final GsonFactory jsonFactory = new GsonFactory();
    private final NetHttpTransport transport = new NetHttpTransport();

    private static final String GOOGLE_USER_INFO_SESSION_KEY = "googleUserInfo";
    private static final String SYSTEM_USER_INFO_SESSION_KEY = "systemUserInfo";

    private static final String JWT_COOKIE_NAME = "spo-sar";
    private static final int JWT_COOKIE_MAX_AGE_SECONDS = 1000 * 60 * 60 * 24;


    @Override
    public ResponseEntity<Map<String, Object>> storeGoogleCredentials(Map<String, String> payload, HttpSession session) throws GeneralSecurityException, IOException {
        return null;
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
