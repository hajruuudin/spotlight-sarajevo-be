package ba.spotlightsarajevo.rest;

import ba.spotlightsarajevo.dao.models.intermediate.GoogleUserModel;
import ba.spotlightsarajevo.dao.models.intermediate.PreferencesModel;
import ba.spotlightsarajevo.dao.models.intermediate.SystemUserModel;
import ba.spotlightsarajevo.dao.models.user.AuthenticatedUser;
import ba.spotlightsarajevo.dao.models.user.SystemLogin;
import ba.spotlightsarajevo.service.definition.service.AuthService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Map;

@Tag(name = "Authentication", description = "Authentication API")
@RequestMapping(value = "/auth")
@AllArgsConstructor
@RestController
public class AuthRestController {
    private AuthService authService;

    private final GsonFactory jsonFactory = new GsonFactory();
    private final NetHttpTransport transport = new NetHttpTransport();

    private static final String GOOGLE_USER_INFO_SESSION_KEY = "googleUserInfo";
    private static final String SYSTEM_USER_INFO_SESSION_KEY = "systemUserInfo";

    @Operation(description = "Register the users Google Credentials into the session")
    @PostMapping(value = "/google/register")
    public ResponseEntity<?> storeGoogleCredentials(
            @RequestBody Map<String, String> payload, HttpSession session) throws GeneralSecurityException, IOException {
        return authService.storeGoogleCredentials(payload, session);
    }

    @Operation(description = "Register the users personal Credentials into the session")
    @PostMapping(value = "/system/register")
    public ResponseEntity<?> storeSystemCredentials(
            @RequestBody SystemUserModel payload, HttpSession session
    ){
        System.out.println("IS THIS ROUTE EVEN TARGETED");
        return authService.storeSystemCredentials(payload, session);
    };

    @Operation(description = "Register a user with their respective Credentials along with the survey data")
    @PostMapping(value = "/register")
    public ResponseEntity<?> register(
            @RequestBody PreferencesModel request, HttpSession session
    ){
        if(session.getAttribute(GOOGLE_USER_INFO_SESSION_KEY) != null){
            GoogleUserModel googleData = (GoogleUserModel) session.getAttribute(GOOGLE_USER_INFO_SESSION_KEY);

            return authService.registerByGoogle(googleData, request);
        } else if (session.getAttribute(SYSTEM_USER_INFO_SESSION_KEY) != null){
            SystemUserModel systemData = (SystemUserModel) session.getAttribute(SYSTEM_USER_INFO_SESSION_KEY);

            return authService.registerBySystem(systemData, request);
        } else {
            throw new InternalException("Internal server error");
        }

    }

    @Operation(description = "Login a user using Google Credentials")
    @PostMapping(value = "/login/google")
    public ResponseEntity<?> loginGoogle(
            @RequestBody Map<String, String> payload, HttpServletResponse servletResponse) throws GeneralSecurityException, IOException {
        return authService.loginGoogle(payload, servletResponse);
    }

    @Operation(description = "Login a user with System Credentials")
    @PostMapping(value = "/login/system")
    public ResponseEntity<?> loginSystem(
            @RequestBody SystemLogin request, HttpServletResponse servletResponse){
        return authService.loginSystem(request, servletResponse);
    }

    @Operation(description = "Check if an email is already associated with the provided email")
    @GetMapping(value = "/check-email")
    public ResponseEntity<?> checkEmail(
            @RequestParam String email){
        return authService.checkEmail(email);
    }
}