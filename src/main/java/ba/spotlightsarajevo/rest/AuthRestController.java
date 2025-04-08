package ba.spotlightsarajevo.rest;

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
    private final GsonFactory jsonFactory = new GsonFactory();
    private final NetHttpTransport transport = new NetHttpTransport();

    private static final String GOOGLE_USER_INFO_SESSION_KEY = "googleUserInfo";

    @Operation(description = "Register using Google Credentials")
    @PostMapping(value = "/google/register")
    public ResponseEntity<?> registerWithGoogle(@RequestBody Map<String, String> payload, HttpSession session) {
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
                Map<String, Object> googleUserInfo = new HashMap<>();
                googleUserInfo.put("googleId", googleId);
                googleUserInfo.put("email", email);
                googleUserInfo.put("firstName", firstName);
                googleUserInfo.put("lastName", lastName);
                googleUserInfo.put("googlePictureUrl", pictureUrl);
                googleUserInfo.put("locale", locale);

                session.setAttribute(GOOGLE_USER_INFO_SESSION_KEY, googleUserInfo);

                for(String info : googleUserInfo.keySet()){
                    System.out.println(googleUserInfo.get(info));
                }
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
}