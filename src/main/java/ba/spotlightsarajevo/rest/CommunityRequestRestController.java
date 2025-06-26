package ba.spotlightsarajevo.rest;

import ba.spotlightsarajevo.dao.models.communityrequest.CommunityRequestCreate;
import ba.spotlightsarajevo.dao.models.communityrequest.CommunityRequestModel;
import ba.spotlightsarajevo.service.definition.service.CommunityRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "Community Request", description = "Community Request API")
@RequestMapping(value = "/community-request")
@AllArgsConstructor
@RestController
public class CommunityRequestRestController {
    CommunityRequestService communityRequestService;

    @Operation(description = "Get all community requests from the database")
    @GetMapping(value = "/admin/get-all")
    public ResponseEntity<List<CommunityRequestModel>> getAll(Principal principal){
        return communityRequestService.findAll(principal);
    }

    @Operation(description = "Add a new community request")
    @PostMapping
    public ResponseEntity<CommunityRequestModel> create(@RequestBody CommunityRequestCreate requestCreate, Principal principal){
        return communityRequestService.create(requestCreate, principal);
    }

    @Operation(description = "Remove a community request")
    @DeleteMapping(value = "/admin/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id, Principal principal){
        return communityRequestService.delete(id, principal);
    }
}
