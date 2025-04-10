package ba.spotlightsarajevo.service.implementation.service;

import ba.spotlightsarajevo.dao.UserDAO;
import ba.spotlightsarajevo.dao.UserFavouriteCategoriesDAO;
import ba.spotlightsarajevo.dao.UserPreferencesDAO;
import ba.spotlightsarajevo.dao.entities.UserEntity;
import ba.spotlightsarajevo.dao.entities.UserFavouriteCategoriesEntity;
import ba.spotlightsarajevo.dao.entities.UserPreferencesEntity;
import ba.spotlightsarajevo.dao.models.intermediate.GoogleUserModel;
import ba.spotlightsarajevo.dao.models.intermediate.PreferencesModel;
import ba.spotlightsarajevo.dao.models.intermediate.SystemUserModel;
import ba.spotlightsarajevo.dao.models.user.SystemLogin;
import ba.spotlightsarajevo.service.definition.service.AuthService;
import ba.spotlightsarajevo.utils.JwtUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    UserDAO userDAO;
    UserPreferencesDAO userPreferencesDAO;
    UserFavouriteCategoriesDAO userFavouriteCategoriesDAO;
    UserDetailsService userDetailsService;
    JwtUtil jwtUtil;

    private final GsonFactory jsonFactory = new GsonFactory();
    private final NetHttpTransport transport = new NetHttpTransport();

    private static final String GOOGLE_USER_INFO_SESSION_KEY = "googleUserInfo";
    private static final String SYSTEM_USER_INFO_SESSION_KEY = "systemUserInfo";

    private static final String JWT_COOKIE_NAME = "spo-sar";
    private static final int JWT_COOKIE_MAX_AGE_SECONDS = 60 * 60 * 24; //1 HOUR

    @Override
    public ResponseEntity<?> storeGoogleCredentials(Map<String, String> payload, HttpSession session) throws GeneralSecurityException, IOException {
        String idTokenString = payload.get("idToken");

        if (idTokenString == null) {
            return new ResponseEntity<>(Map.of("success", false, "message", "ID token missing."), HttpStatus.BAD_REQUEST);
        }
        GoogleIdToken idToken = verifyGoogleToken(idTokenString);

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

    }

    @Override
    public ResponseEntity<?> storeSystemCredentials(SystemUserModel payload, HttpSession session){
        if(payload == null){
            throw new IllegalArgumentException("The register payload cannot be null");
        }


        String hashedPassword = BCrypt.hashpw(payload.getPassword(), BCrypt.gensalt());
        payload.setPassword(hashedPassword);

        System.out.println("Payload should be: " + payload);

        session.setAttribute(SYSTEM_USER_INFO_SESSION_KEY, payload);
        System.out.println(session.getAttribute(SYSTEM_USER_INFO_SESSION_KEY));

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "System authentication successful. User data stored temporarily. Proceed with survey.",
                "payload", payload
        ));
    }

    @Override
    public ResponseEntity<?> registerByGoogle(GoogleUserModel googleData, PreferencesModel preferences) {

        /* USER ENTITY */
        UserEntity userEntity = new UserEntity();
        userEntity.setGoogleId(googleData.getGoogleId());
        userEntity.setFirstName(googleData.getFirstName());
        userEntity.setLastName(googleData.getLastName());
        userEntity.setGooglePictureUrl(googleData.getGooglePictureUrl());
        userEntity.setGoogleEmail(googleData.getEmail());
        userEntity.setLocale(googleData.getLocale());
        userEntity.setIsAdmin(false);
        userEntity.setIsPremium(false);
        userEntity.setRegistrationType("system");
        userEntity.setCreatedAt(LocalDateTime.now());

        try {
            userDAO.persist(userEntity);
        } catch (Error e){
            throw new IllegalArgumentException("Error while persisting User Entity");
        }


        /* USER FAVOURITE CATEGORIES ENTITIES */
        UserFavouriteCategoriesEntity userCategory01 = new UserFavouriteCategoriesEntity();
        userCategory01.setUserId(userEntity.getId());
        userCategory01.setCategoryId(preferences.getCategory_id_1());
        userFavouriteCategoriesDAO.persist(userCategory01);

        UserFavouriteCategoriesEntity userCategory02 = new UserFavouriteCategoriesEntity();
        userCategory02.setUserId(userEntity.getId());
        userCategory02.setCategoryId(preferences.getCategory_id_2());
        userFavouriteCategoriesDAO.persist(userCategory02);

        UserFavouriteCategoriesEntity userCategory03 = new UserFavouriteCategoriesEntity();
        userCategory03.setUserId(userEntity.getId());
        userCategory03.setCategoryId(preferences.getCategory_id_3());
        userFavouriteCategoriesDAO.persist(userCategory03);

        /* USER PREFERENCES MODEL */
        UserPreferencesEntity userPreferences = new UserPreferencesEntity();
        userPreferences.setUserId(userEntity.getId());
        userPreferences.setLanguage(preferences.getLanguage());
        userPreferences.setAnswer01(preferences.getAnswer01());
        userPreferences.setAnswer02(preferences.getAnswer02());
        userPreferences.setAnswer03(preferences.getAnswer03());
        userPreferences.setAnswer04(preferences.getAnswer04());
        userPreferencesDAO.persist(userPreferences);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Data stored! USER - USER PREFERENCES - USER CATEGORIES",
                "payload", userEntity
        ));
    }

    @Override
    public ResponseEntity<?> registerBySystem(SystemUserModel systemData, PreferencesModel preferences) {
        /* USER ENTITY */
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(systemData.getEmail());
        userEntity.setPassword(systemData.getPassword());
        userEntity.setFirstName(systemData.getFirstName());
        userEntity.setLastName(systemData.getLastName());
        userEntity.setIsAdmin(false);
        userEntity.setIsPremium(false);
        userEntity.setRegistrationType("system");
        userEntity.setCreatedAt(LocalDateTime.now());

        try {
            userDAO.persist(userEntity);
        } catch (Error e){
            throw new IllegalArgumentException("Error while persisting User Entity");
        }


        /* USER FAVOURITE CATEGORIES ENTITIES */
        UserFavouriteCategoriesEntity userCategory01 = new UserFavouriteCategoriesEntity();
        userCategory01.setUserId(userEntity.getId());
        userCategory01.setCategoryId(preferences.getCategory_id_1());
        userFavouriteCategoriesDAO.persist(userCategory01);

        UserFavouriteCategoriesEntity userCategory02 = new UserFavouriteCategoriesEntity();
        userCategory02.setUserId(userEntity.getId());
        userCategory02.setCategoryId(preferences.getCategory_id_2());
        userFavouriteCategoriesDAO.persist(userCategory02);

        UserFavouriteCategoriesEntity userCategory03 = new UserFavouriteCategoriesEntity();
        userCategory03.setUserId(userEntity.getId());
        userCategory03.setCategoryId(preferences.getCategory_id_3());
        userFavouriteCategoriesDAO.persist(userCategory03);

        /* USER PREFERENCES MODEL */
        UserPreferencesEntity userPreferences = new UserPreferencesEntity();
        userPreferences.setUserId(userEntity.getId());
        userPreferences.setLanguage(preferences.getLanguage());
        userPreferences.setAnswer01(preferences.getAnswer01());
        userPreferences.setAnswer02(preferences.getAnswer02());
        userPreferences.setAnswer03(preferences.getAnswer03());
        userPreferences.setAnswer04(preferences.getAnswer04());
        userPreferencesDAO.persist(userPreferences);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Data stored! USER - USER PREFERENCES - USER CATEGORIES",
                "payload", userEntity
        ));
    }

    @Override
    public ResponseEntity<?> loginGoogle(Map<String, String> payload, HttpServletResponse servletResponse) throws GeneralSecurityException, IOException {
        String idTokenString = payload.get("idToken");

        if (idTokenString == null) {
            throw new IllegalArgumentException("ID Token cannot be null");
        }

        GoogleIdToken token = verifyGoogleToken(idTokenString);

        if (token != null) {
            GoogleIdToken.Payload tokenPayload = token.getPayload();
            String googleId = tokenPayload.getSubject();
            String email = tokenPayload.getEmail();

            UserEntity userEntity = userDAO.findByGoogleId(googleId);

            if (userEntity != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                String jwt = jwtUtil.generateToken(userDetails);
                setJwtCookie(servletResponse, jwt);
                return ResponseEntity.ok(Map.of("token", jwt));
            } else {
                return ResponseEntity.status(404).body(Map.of("message", "Google user not found"));
            }
        } else {
            throw new GeneralSecurityException("Error ovo treba skontat kasnije");
        }
    }

    @Override
    public ResponseEntity<?> loginSystem(SystemLogin request, HttpServletResponse servletResponse) {
        if(request == null){
            return ResponseEntity.status(403).body(Map.of("message", "Credentials invalid!"));
        }
        UserEntity entity = userDAO.findBySystemEmail(request.getEmail());

        String storedPassword = entity.getPassword();
        String providedPassword = request.getPassword();

        if (BCrypt.checkpw(providedPassword, storedPassword)) {
            System.out.println("Password match, in method");
            System.out.println(request.getEmail());
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            String jwt = jwtUtil.generateToken(userDetails);
            setJwtCookie(servletResponse, jwt);
            return ResponseEntity.ok(Map.of("token", jwt));
        } else {
            return ResponseEntity.status(403).body(Map.of("message", "Password do not match"));

        }
    }

    @Override
    public ResponseEntity<?> checkEmail(String email){
        UserEntity userEntity = userDAO.findByEmail(email);

        if(userEntity != null){
            return  ResponseEntity.status(409).body(Map.of("message", "Email already taken"));
        } else {
            return ResponseEntity.status(200).body(Map.of("message", "Email not taken. Proceed"));
        }
    }

    private GoogleIdToken verifyGoogleToken(String idTokenString) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList("564056268905-9j2u47hutljdrv2rgepipilpgp2ogh3e.apps.googleusercontent.com"))
                .build();

        return verifier.verify(idTokenString);
    }

    private void setJwtCookie(HttpServletResponse response, String token) {
        Cookie jwtCookie = new Cookie(JWT_COOKIE_NAME, token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setMaxAge(JWT_COOKIE_MAX_AGE_SECONDS);
        jwtCookie.setPath("/");
        // jwtCookie.setSecure(true); ako se odlucim za HTTPS kasnije
        response.addCookie(jwtCookie);
    }
}
