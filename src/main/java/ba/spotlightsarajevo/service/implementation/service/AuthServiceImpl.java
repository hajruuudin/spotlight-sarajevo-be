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
import ba.spotlightsarajevo.service.definition.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    UserDAO userDAO;
    UserPreferencesDAO userPreferencesDAO;
    UserFavouriteCategoriesDAO userFavouriteCategoriesDAO;

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
        userEntity.setRegistrationType("google");
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
}
