package ba.spotlightsarajevo.service.implementation.service;

import ba.spotlightsarajevo.dao.CollectionDAO;
import ba.spotlightsarajevo.dao.UserDAO;
import ba.spotlightsarajevo.dao.UserFavouriteCategoriesDAO;
import ba.spotlightsarajevo.dao.UserPreferencesDAO;
import ba.spotlightsarajevo.dao.entities.CollectionEntity;
import ba.spotlightsarajevo.dao.entities.UserEntity;
import ba.spotlightsarajevo.dao.entities.UserFavouriteCategoriesEntity;
import ba.spotlightsarajevo.dao.entities.UserPreferencesEntity;
import ba.spotlightsarajevo.dao.models.intermediate.GoogleUserModel;
import ba.spotlightsarajevo.dao.models.intermediate.PreferencesModel;
import ba.spotlightsarajevo.dao.models.intermediate.SystemUserModel;
import ba.spotlightsarajevo.dao.models.user.LoggedUserModel;
import ba.spotlightsarajevo.dao.models.user.SystemLogin;
import ba.spotlightsarajevo.dao.models.user.UserModel;
import ba.spotlightsarajevo.enums.ObjectType;
import ba.spotlightsarajevo.service.definition.mapper.UserMapper;
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
    UserMapper userMapper;
    UserPreferencesDAO userPreferencesDAO;
    UserFavouriteCategoriesDAO userFavouriteCategoriesDAO;
    UserDetailsService userDetailsService;
    CollectionDAO collectionDAO;
    JwtUtil jwtUtil;

    private final GsonFactory jsonFactory = new GsonFactory();
    private final NetHttpTransport transport = new NetHttpTransport();

    private static final String GOOGLE_USER_INFO_SESSION_KEY = "googleUserInfo";
    private static final String SYSTEM_USER_INFO_SESSION_KEY = "systemUserInfo";

    private static final String JWT_COOKIE_NAME = "spo-sar";
    private static final int JWT_COOKIE_MAX_AGE_SECONDS = 60 * 60 * 24; //1 HOUR

    @Override
    public ResponseEntity<Map<String, Object>> storeGoogleCredentials(Map<String, String> payload, HttpSession session) throws GeneralSecurityException, IOException {
        String idTokenString = payload.get("idToken");

        if (idTokenString == null) {
            return ResponseEntity.status(400).body(Map.of("message", "There was an error with the google request"));
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

                UserEntity checkUserEntity = userDAO.findByGoogleId(googleId);

                if(checkUserEntity != null){
                    return ResponseEntity.status(400).body(Map.of("message", "An account is already registered to that email. Please log in!"));
                }

                // Store this information in the session temporarily
                GoogleUserModel googleUserInfo = new GoogleUserModel();
                googleUserInfo.setGoogleId(googleId);
                googleUserInfo.setEmail(email);
                googleUserInfo.setFirstName(firstName);
                googleUserInfo.setLastName(lastName);
                googleUserInfo.setGooglePictureUrl(pictureUrl);
                googleUserInfo.setLocale(locale);

                session.setAttribute(GOOGLE_USER_INFO_SESSION_KEY, googleUserInfo);

            return ResponseEntity.status(200).body(Map.of("message", "Successfully stored Google data to session", "firstName", googleUserInfo.getFirstName()));
        } else {
            return ResponseEntity.status(500).body(Map.of("message", "There was an error with the google request"));
        }

    }

    @Override
    public ResponseEntity<Map<String, Object>> storeSystemCredentials(SystemUserModel payload, HttpSession session){
        if(payload == null){
            return ResponseEntity.status(400).body(Map.of("message", "The credentials cannot be null!"));
        }

        String hashedPassword = BCrypt.hashpw(payload.getPassword(), BCrypt.gensalt());
        payload.setPassword(hashedPassword);

        session.setAttribute(SYSTEM_USER_INFO_SESSION_KEY, payload);

        return ResponseEntity.status(200).body(Map.of("message", "Successfully stored System data to session", "firstName", payload.getFirstName()));
    }

    @Override
    public ResponseEntity<UserModel> registerByGoogle(GoogleUserModel googleData, PreferencesModel preferences) {
        /* Setting the database properties for a Google user entity */
        UserEntity userEntity = new UserEntity();
        userEntity.setGoogleId(googleData.getGoogleId());
        userEntity.setFirstName(googleData.getFirstName());
        userEntity.setLastName(googleData.getLastName());
        userEntity.setGooglePictureUrl(googleData.getGooglePictureUrl());
        userEntity.setGoogleEmail(googleData.getEmail());
        userEntity.setLocale(googleData.getLocale());
        userEntity.setIsAdmin(false);
        userEntity.setIsPremium(false);
        userEntity.setRegistrationType("google");
        userEntity.setCreatedAt(LocalDateTime.now());

        try {
            userDAO.save(userEntity);
        } catch (Error e){
            throw new IllegalArgumentException("Error while persisting User Entity");
        }

        /* Setting the favourite categories and preferences of the user */
        setUserFavouriteCategoriesAndPreferences(userEntity, preferences);

        /* Creating the two default collections for the user  */
        createUserDefaultCollections(userEntity);

        return ResponseEntity.ok(userMapper.entityToDto(userEntity));
    }

    @Override
    public ResponseEntity<UserModel> registerBySystem(SystemUserModel systemData, PreferencesModel preferences) {
        /* Setting the database properties for a system user entity */
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
            userDAO.save(userEntity);
        } catch (Error e){
            throw new IllegalArgumentException("Error while persisting User Entity");
        }

        /* Setting the favourite categories and preferences of the user */
        setUserFavouriteCategoriesAndPreferences(userEntity, preferences);

        /* Creating the two default collections for the user  */
        createUserDefaultCollections(userEntity);

        return ResponseEntity.ok(userMapper.entityToDto(userEntity));
    }

    @Override
    public ResponseEntity<Map<String, Object>> loginGoogle(Map<String, String> payload, HttpServletResponse servletResponse) throws GeneralSecurityException, IOException {
        String idTokenString = payload.get("idToken");

        if (idTokenString == null) {
            return ResponseEntity.status(400).body(Map.of("message", "There was an error with the google request"));
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
                System.out.println(userEntity);
                return ResponseEntity.ok(Map.of(
                        "token", jwt,
                        "user", new LoggedUserModel(
                                userEntity.getId(),
                                userEntity.getFirstName(),
                                userEntity.getLastName(),
                                userEntity.getGoogleEmail(),
                                userEntity.getIsAdmin(),
                                userEntity.getIsPremium(),
                                userEntity.getGooglePictureUrl()
                        )
                ));
            } else {
                return ResponseEntity.status(400).body(Map.of("message", "Google user not registered! Please register"));
            }
        } else {
            return ResponseEntity.status(400).body(Map.of("message", "There was an error with the google request"));
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> loginSystem(SystemLogin request, HttpServletResponse servletResponse) {
        if(request == null){
            return ResponseEntity.status(403).body(Map.of("message", "Invalid credentials!"));
        }
        UserEntity entity = userDAO.findBySystemEmail(request.getEmail());

        String storedPassword = entity.getPassword();
        String providedPassword = request.getPassword();

        if (BCrypt.checkpw(providedPassword, storedPassword)) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            String jwt = jwtUtil.generateToken(userDetails);
            setJwtCookie(servletResponse, jwt);
            System.out.println(entity);
            return ResponseEntity.ok(Map.of(
                    "token", jwt,
                    "user", new LoggedUserModel(
                            entity.getId(),
                            entity.getFirstName(),
                            entity.getLastName(),
                            entity.getEmail(),
                            entity.getIsAdmin(),
                            entity.getIsPremium(),
                            "null" //REPLACE WITH LOOKUP IMAGE SERVICE
                    )
            ));
        } else {
            return ResponseEntity.status(403).body(Map.of("message", "Invalid credentials!"));
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> checkEmail(String email){
        UserEntity userEntity = userDAO.findByEmail(email);

        if(userEntity != null){
            return  ResponseEntity.status(409).body(Map.of("message", "Email already taken!"));
        } else {
            return ResponseEntity.status(200).body(Map.of("message", "Email not taken. Proceed"));
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> logout(HttpServletResponse response) {
        Cookie deleteCookie = new Cookie(JWT_COOKIE_NAME, null);
        deleteCookie.setPath("/");
        deleteCookie.setHttpOnly(true);
        deleteCookie.setMaxAge(0);

        String appEnv = System.getenv("APP_ENVIRONMENT");
        StringBuilder cookieHeader = new StringBuilder(deleteCookie.getName() + "=; Path=" + deleteCookie.getPath() + "; HttpOnly; Max-Age=0");

        if ("production".equals(appEnv)) {
            cookieHeader.append("; Secure");
            cookieHeader.append("; SameSite=None");
        }

        response.setHeader("Set-Cookie", cookieHeader.toString());

        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
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

    private void setUserFavouriteCategoriesAndPreferences(UserEntity entity, PreferencesModel preferences){
        /* USER FAVOURITE CATEGORIES ENTITIES */
        UserFavouriteCategoriesEntity userCategory01 = new UserFavouriteCategoriesEntity();
        userCategory01.setUserId(entity.getId());
        userCategory01.setCategoryId(preferences.getCategory_id_1());
        userFavouriteCategoriesDAO.save(userCategory01);

        UserFavouriteCategoriesEntity userCategory02 = new UserFavouriteCategoriesEntity();
        userCategory02.setUserId(entity.getId());
        userCategory02.setCategoryId(preferences.getCategory_id_2());
        userFavouriteCategoriesDAO.save(userCategory02);

        UserFavouriteCategoriesEntity userCategory03 = new UserFavouriteCategoriesEntity();
        userCategory03.setUserId(entity.getId());
        userCategory03.setCategoryId(preferences.getCategory_id_3());
        userFavouriteCategoriesDAO.save(userCategory03);

        /* USER PREFERENCES MODEL */
        UserPreferencesEntity userPreferences = new UserPreferencesEntity();
        userPreferences.setUserId(entity.getId());
        userPreferences.setLanguage(preferences.getLanguage());
        userPreferences.setAnswer01(preferences.getAnswer01());
        userPreferences.setAnswer02(preferences.getAnswer02());
        userPreferences.setAnswer03(preferences.getAnswer03());
        userPreferences.setAnswer04(preferences.getAnswer04());
        userPreferencesDAO.save(userPreferences);
    }

    private void createUserDefaultCollections(UserEntity entity){
        CollectionEntity collectionEventEntity = new CollectionEntity();
        collectionEventEntity.setUserId(entity.getId());
        collectionEventEntity.setCollectionType(ObjectType.EVENT);
        collectionEventEntity.setCollectionName("All Events");

        CollectionEntity collectionSpotEntity = new CollectionEntity();
        collectionSpotEntity.setUserId(entity.getId());
        collectionSpotEntity.setCollectionType(ObjectType.SPOT);
        collectionSpotEntity.setCollectionName("All Spots");

        try{
            collectionDAO.save(collectionEventEntity);
            collectionDAO.save(collectionSpotEntity);
        } catch (Exception e){
            throw new RuntimeException("ERROR: Collection Create");
        }
    }
}
