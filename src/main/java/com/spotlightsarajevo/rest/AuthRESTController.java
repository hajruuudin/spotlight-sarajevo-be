package com.spotlightsarajevo.rest;

import com.spotlightsarajevo.service.definition.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @PostMapping(value = "/storeGoogleCredentials")
    @Operation(description = "Send the ID token from Google over the frontend to the backend and register the users initial credentials")
    public ResponseEntity<Map<String, Object>> storeGoogleCredentials(@RequestBody Map<String, String> payload, HttpSession session) throws GeneralSecurityException, IOException {
        System.out.println("THIS IS BEING HIT:" + payload.get("idToken"));
        return authService.storeGoogleCredentials(payload, session);
    }
}
