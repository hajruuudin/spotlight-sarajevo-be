package ba.spotlightsarajevo.service.definition.service;

import ba.spotlightsarajevo.dao.models.intermediate.GoogleUserModel;
import ba.spotlightsarajevo.dao.models.intermediate.PreferencesModel;
import ba.spotlightsarajevo.dao.models.intermediate.SystemUserModel;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> registerByGoogle(GoogleUserModel googleData, PreferencesModel preferences);
    ResponseEntity<?> registerBySystem(SystemUserModel systemData, PreferencesModel preferencesModel);
}
