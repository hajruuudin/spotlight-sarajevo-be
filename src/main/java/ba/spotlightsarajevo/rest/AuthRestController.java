package ba.spotlightsarajevo.rest;

import ba.spotlightsarajevo.dao.models.intermediate.GoogleUserModel;
import ba.spotlightsarajevo.dao.models.intermediate.PreferencesModel;
import ba.spotlightsarajevo.dao.models.intermediate.SystemUserModel;
import ba.spotlightsarajevo.service.definition.service.AuthService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
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
    public ResponseEntity<?> registerSessionWithGoogle(
            @RequestBody Map<String, String> payload, HttpSession session)
    {
        String idTokenString = payload.get("idToken");

        if (idTokenString == null) {
            return new ResponseEntity<>(Map.of("success", false, "message", "ID token missing."), HttpStatus.BAD_REQUEST);
        }

        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections.singletonList("564056268905-9j2u47hutljdrv2rgepipilpgp2ogh3e.apps.googleusercontent.com"))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                GoogleIdToken.Payload tokenPayload = idToken.getPayload();

                // Extract user information
                String googleId = tokenPayload.getSubject();
                String email = tokenPayload.getEmail();
                String firstName = (String) tokenPayload.get("given_name");
                String lastName = (String) tokenPayload.get("family_name");
                String pictureUrl = (String) tokenPayload.get("picture");
                String locale = (String) tokenPayload.get("locale");

                // Store this information in the session temporarily
                GoogleUserModel googleUserInfo = new GoogleUserModel();
                googleUserInfo.setGoogleId(googleId);
                googleUserInfo.setEmail(email);
                googleUserInfo.setFirstName(firstName);
                googleUserInfo.setLastName(lastName);
                googleUserInfo.setGooglePictureUrl(pictureUrl);
                googleUserInfo.setLocale(locale);

                session.setAttribute(GOOGLE_USER_INFO_SESSION_KEY, googleUserInfo);


                System.out.println(session.getAttribute(GOOGLE_USER_INFO_SESSION_KEY));
                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "Google authentication successful. User data stored temporarily. Proceed with survey.",
                        "firstName", firstName
                ));
            } else {
                return new ResponseEntity<>(Map.of(
                        "success", false,
                        "message", "Invalid Google ID token."
                ), HttpStatus.UNAUTHORIZED);
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(Map.of(
                    "success", false,
                    "message", "Error verifying Google ID token."
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(description = "Register the users personal Credentials into the session")
    @PostMapping(value = "/system/register")
    public ResponseEntity<?> registerSessionWithSystem(
            @RequestBody SystemUserModel payload, HttpSession session
    ){
        if(payload == null){
            throw new IllegalArgumentException("The register payload cannot be null");
        }

        String hashedPassword = BCrypt.hashpw(payload.getPassword(), BCrypt.gensalt());
        payload.setPassword(hashedPassword);

        session.setAttribute(SYSTEM_USER_INFO_SESSION_KEY, payload);
        System.out.println(session.getAttribute(SYSTEM_USER_INFO_SESSION_KEY));

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "System authentication successful. User data stored temporarily. Proceed with survey.",
                "payload", payload
        ));
    };

    @Operation(description = "Register a user with their respective Credentials along with the survey data")
    @PostMapping(value = "/register")
    public ResponseEntity<?> register(
            @RequestBody PreferencesModel request, HttpSession session
    ){
        if(session.getAttribute(GOOGLE_USER_INFO_SESSION_KEY) != null){
            GoogleUserModel googleData = (GoogleUserModel) session.getAttribute(GOOGLE_USER_INFO_SESSION_KEY);

            return authService.registerByGoogle(googleData, request);
        }

        if(session.getAttribute(SYSTEM_USER_INFO_SESSION_KEY) != null){
            SystemUserModel systemData = (SystemUserModel) session.getAttribute(SYSTEM_USER_INFO_SESSION_KEY);

            return authService.registerBySystem(systemData, request);
        }


        System.out.println(session.getAttribute(GOOGLE_USER_INFO_SESSION_KEY));
        return null;
    }
}