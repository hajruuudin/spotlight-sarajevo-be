package ba.spotlightsarajevo.service.definition.service;

import ba.spotlightsarajevo.dao.models.spot.SpotCreate;
import ba.spotlightsarajevo.dao.models.spot.SpotModel;
import ba.spotlightsarajevo.utils.EntityRequest;
import ba.spotlightsarajevo.utils.EntityResponse;

public interface SpotService {
    EntityResponse<SpotModel> create(final EntityRequest<SpotCreate> request);

    EntityResponse<SpotModel> findById(final EntityRequest<Integer> request);
}
