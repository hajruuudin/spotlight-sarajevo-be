package com.spotlightsarajevo.modules.community.api;

import com.spotlightsarajevo.modules.community.api.dto.*;
import com.spotlightsarajevo.modules.community.service.CommunityRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@Tag(name = "Community Request API", description = "API for managing community requests")
@AllArgsConstructor
@RequestMapping(value = "/community-request")
public class CommunityRequestRESTController {
    CommunityRequestService communityRequestService;

    @GetMapping(value = "/get-user-requests")
    @Operation(description = "This API endpoint retrieves all the community requests made by the authenticated user from the database view")
    public ResponseEntity<List<UserCommunityRequestModel>> getUserCommunityRequests(
            Principal principal
    ) {
        return communityRequestService.getUserCommunityRequests(principal);
    }

    @PostMapping(value = "/create-request")
    @Operation(description = "This API endpoint allows an authenticated user to create a new community request")
    public ResponseEntity<CommunityRequestModel> createCommunityRequest(
            @RequestBody CommunityRequestCreateModel request,
            Principal principal
    ){
        return communityRequestService.createCommunityRequest(request, principal);
    }

    /* ADMIN ROUTES */
    @GetMapping(value = "/admin/get-all-requests")
    @Operation(description = "This API endpoint retrieves all community requests from the database view for administrative purposes")
    public ResponseEntity<List<CommunityRequestModel>> getAllCommunityRequests(
            Principal principal
    ){
        return communityRequestService.getAllCommunityRequests(principal);
    }

    /* TODO: Could potentially be a bug later on. Might need to modify the DTOs */
    @GetMapping(value = "/admin/get-request/{requestId}")
    @Operation(description = "This API endpoint retrieves the details of a specific community request by its ID for administrative purposes")
    public ResponseEntity<CommunityRequestDataModel> getCommunityRequestById(
            @PathVariable Integer requestId,
            Principal principal
    ) {
        return communityRequestService.getCommunityRequestById(requestId, principal);
    }

    @PutMapping(value = "/admin/update-request-status")
    @Operation(description = "This API endpoint updates the status of the specified community request")
    public ResponseEntity<CommunityRequestModel> updateCommunityRequestStatus(
            CommunityRequestStatusUpdate request,
            Principal principal
    ) {
        return communityRequestService.updateCommunityRequestStatus(request, principal);
    }

    @DeleteMapping(value = "/admin/delete-request/{requestId}")
    @Operation(description = "This API endpoint deletes the specified community request from the database")
    public ResponseEntity<CommunityRequestModel> deleteCommunityRequest(
            @PathVariable Integer requestId,
            Principal principal
    ) {
        return communityRequestService.deleteCommunityRequest(requestId, principal);
    }
}
