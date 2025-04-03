package ba.spotlightsarajevo.service.definition.validator;

import ba.spotlightsarajevo.dao.models.spot.SpotCreate;

public interface SpotValidator {
    void validateCreateRequest(SpotCreate request);
}
