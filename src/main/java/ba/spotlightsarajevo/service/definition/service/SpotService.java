package ba.spotlightsarajevo.service.definition.service;

import ba.spotlightsarajevo.dao.models.spot.SpotCreate;
import ba.spotlightsarajevo.dao.models.spot.SpotModel;
import ba.spotlightsarajevo.utils.SSEntityRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

public interface SpotService {
    ResponseEntity<SpotModel> create(final SSEntityRequest<SpotCreate> request);

    ResponseEntity<Page<SpotModel>> getSpotsPaginated(PageRequest request);

    ResponseEntity<SpotModel> findBySlug(final SSEntityRequest<String> request);
}
