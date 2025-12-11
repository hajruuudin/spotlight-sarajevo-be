package com.spotlightsarajevo.modules.review.service;

import com.spotlightsarajevo.modules.review.api.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface ReviewService {
    /* ============================================================= */
    /* ==================== SPOT REVIEWS CRUD ====================== */
    /* ============================================================= */

    ResponseEntity<Page<SpotReviewModel>> findReviewsForSpot(PageRequest pageRequest, Integer spotId, String sortOption);

    ResponseEntity<SpotReviewModel> findUserReview(Integer spotId, Principal principal);

    ResponseEntity<SpotReviewModel> createSpotReview(SpotReviewCreateModel createRequest, Principal principal);

    ResponseEntity<SpotReviewModel> updateSpotReview(SpotReviewUpdateModel updateRequest, Principal principal);

    ResponseEntity<SpotReviewModel> deleteSpotReview(Integer spotId, Integer reviewId, Principal principal);

    /* ======================================================================== */
    /* ==================== EVENT ORGANISER REVIEWS CRUD ====================== */
    /* ======================================================================== */

    ResponseEntity<Page<EventOrganiserReviewModel>> findReviewsForOrganiser(PageRequest pageRequest, Integer organiserId, String sortOption);

    ResponseEntity<EventOrganiserReviewModel> findUserOrganiserReview(Integer organiserId, Principal principal);

    ResponseEntity<EventOrganiserReviewModel> createOrganiserReview(EventOrganiserReviewCreateModel request, Principal principal);

    ResponseEntity<EventOrganiserReviewModel> updateOrganiserReview(EventOrganiserReviewUpdateModel request, Principal principal );

    ResponseEntity<EventOrganiserReviewModel> deleteOrganiserReview(Integer organiserId, Integer reviewId, Principal principal);
}
