package ba.spotlightsarajevo.service.definition.service;

import ba.spotlightsarajevo.dao.models.spot.SpotCreate;
import ba.spotlightsarajevo.dao.models.spot.SpotModel;
import ba.spotlightsarajevo.dao.models.spot.SpotShorthand;
import ba.spotlightsarajevo.dao.models.spot.SpotUpdate;
import ba.spotlightsarajevo.utils.SSEntityRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

public interface SpotService {
    ResponseEntity<SpotModel> create(final SSEntityRequest<SpotCreate> request);

    ResponseEntity<Page<SpotModel>> getSpotsPaginated(PageRequest request);

    ResponseEntity<Page<SpotShorthand>> getSpotsShorthand(PageRequest request, String search, String sort, List<Integer> categoryIds);

    ResponseEntity<SpotShorthand> getSpotHeadline();

    ResponseEntity<Page<SpotShorthand>> getCategorisedSpots(PageRequest request, Integer categoryId);

    ResponseEntity<SpotModel> getSpotOverviewBySlug(final SSEntityRequest<String> request);

    ResponseEntity<SpotModel> updateSpot(SpotUpdate request, Principal principal);
}
