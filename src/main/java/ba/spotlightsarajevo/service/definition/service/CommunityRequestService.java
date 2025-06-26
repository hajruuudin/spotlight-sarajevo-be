package ba.spotlightsarajevo.service.definition.service;

import ba.spotlightsarajevo.dao.models.communityrequest.CommunityRequestCreate;
import ba.spotlightsarajevo.dao.models.communityrequest.CommunityRequestModel;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

public interface CommunityRequestService {
    ResponseEntity<CommunityRequestModel> create(CommunityRequestCreate requestCreate, Principal principal);
    ResponseEntity<List<CommunityRequestModel>> findAll(Principal principal); // possible pagination
    ResponseEntity<Boolean> delete(Integer requestId, Principal principal);
}
