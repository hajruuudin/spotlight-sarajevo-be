package ba.spotlightsarajevo.rest;

import ba.spotlightsarajevo.dao.models.communityrequest.CommunityRequestCreate;
import ba.spotlightsarajevo.dao.models.communityrequest.CommunityRequestModel;
import ba.spotlightsarajevo.service.definition.service.CommunityRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Tag(name = "Community Request", description = "Community Request API")
@RequestMapping(value = "/community-request")
@AllArgsConstructor
@RestController
public class CommunityRequestRestController {
    CommunityRequestService communityRequestService;

    @Operation(description = "Add a new community request")
    @PostMapping
    public ResponseEntity<CommunityRequestModel> create(@RequestBody CommunityRequestCreate requestCreate, Principal principal){
        return  communityRequestService.create(requestCreate, principal);
    }
}
