package com.spotlightsarajevo.modules.spot.service;

import com.spotlightsarajevo.modules.spot.api.dto.*;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

/**
 * SpotService interface defines methods for managing and retrieving spot-related data.
 * It includes methods to find all spots, find spots with pagination and filtering,
 * retrieve spot overviews, and create, update, or delete spots.
 */
public interface SpotService {
    /**
     * Retrieves a paginated list of all spots.
     *
     * @param pageRequest the pagination information
     * @return ResponseEntity containing a Page of SpotModel
     */
    ResponseEntity<Page<SpotModel>> findAll(PageRequest pageRequest);

    /**
     * Retrieves a paginated list of spots based on search term, sorting option, and category filters.
     *
     * @param pageRequest the pagination information
     * @param searchTerm the term to search for in spot names or descriptions
     * @param sortOption the option to sort the results
     * @param categoryIds the list of category IDs to filter the spots
     * @return ResponseEntity containing a Page of SpotShorthandModel
     */
    ResponseEntity<Page<SpotShorthandModel>> findSpotsPaginated(PageRequest pageRequest, String searchTerm, String sortOption, List<Integer> categoryIds);

    /**
     * Retrieves an overview of a specific spot by its slug.
     *
     * @param spotSlug the unique slug identifier of the spot
     * @return ResponseEntity containing the SpotOverviewModel
     */
    ResponseEntity<SpotOverviewModel> findSpotOverview(String spotSlug);

    /**
     * Creates a new spot based on the provided SpotCreateModel.
     *
     * @param spotCreateModel the model containing information to create a new spot
     * @return ResponseEntity containing the created SpotModel
     */
    ResponseEntity<SpotModel> create(SpotCreateModel spotCreateModel);

    /**
     * Updates an existing spot based on the provided SpotUpdateModel.
     *
     * @param spotUpdateModel the model containing updated information for the spot
     * @return ResponseEntity containing the updated SpotModel
     */
    ResponseEntity<SpotModel> update(SpotUpdateModel spotUpdateModel);

    /**
     * Deletes a spot by its unique identifier.
     *
     * @param id the unique identifier of the spot to be deleted
     * @return ResponseEntity containing the deleted SpotModel
     */
    ResponseEntity<SpotModel> delete(Integer id);

    /**
     * Checks if the user has marked the spot as visited.
     *
     * @param spotId the unique identifier of the spot
     * @param principal the security principal of the user
     * @return ResponseEntity containing a Boolean indicating visited status
     */
    ResponseEntity<Boolean> getSpotVisitedStatus(Integer spotId, Principal principal);

    /**
     * Retrieves a list of spots for map view based on search term and category filters.
     *
     * @param searchTerm the term to search for in spot names
     * @param categoryIds the list of category IDs to filter the spots
     * @return ResponseEntity containing a List of SpotMapModel
     */
    ResponseEntity<List<SpotMapModel>> findSpotsForMap(String searchTerm, List<Integer> categoryIds);
}
