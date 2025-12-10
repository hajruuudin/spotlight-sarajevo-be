package com.spotlightsarajevo.modules.review.service;

import com.spotlightsarajevo.modules.review.api.dto.SpotReviewCreateModel;
import com.spotlightsarajevo.modules.review.api.dto.SpotReviewModel;
import com.spotlightsarajevo.modules.review.api.dto.SpotReviewUpdateModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface ReviewService {
    ResponseEntity<Page<SpotReviewModel>> findReviewsForSpot(PageRequest pageRequest, Integer spotId, String sortOption);

    ResponseEntity<SpotReviewModel> findUserReview(Integer spotId, Principal principal);

    ResponseEntity<SpotReviewModel> createSpotReview(SpotReviewCreateModel createRequest, Principal principal);

    ResponseEntity<SpotReviewModel> updateSpotReview(SpotReviewUpdateModel updateRequest, Principal principal);

    ResponseEntity<SpotReviewModel> deleteSpotReview(Integer spotId, Integer reviewId, Principal principal);
}
