package ba.spotlightsarajevo.service.implementation.validator;

import ba.spotlightsarajevo.dao.models.spot.SpotCreate;
import ba.spotlightsarajevo.service.definition.validator.SpotValidator;
import org.springframework.stereotype.Service;

@Service
public class SpotValidatorImpl implements SpotValidator {
    @Override
    public void validateCreateRequest(SpotCreate request) {
        if(request.getOfficialName().isEmpty()){
            throw new IllegalArgumentException("CREATEVALIDATON: Name cannot be null");
        }
    }
}
