package com.spotlightsarajevo.modules.spot.service;

import com.spotlightsarajevo.modules.spot.api.dto.*;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

public interface SpotService {
    ResponseEntity<Page<SpotModel>> findAll(PageRequest pageRequest);

    ResponseEntity<Page<SpotShorthandModel>> findSpotsPaginated(PageRequest pageRequest, String searchTerm, String sortOption, List<Integer> categoryIds);

    ResponseEntity<SpotOverviewModel> findSpotOverview(String spotSlug);

    ResponseEntity<SpotModel> create(SpotCreateModel spotCreateModel);

    ResponseEntity<SpotModel> update(SpotUpdateModel spotUpdateModel);

    ResponseEntity<SpotModel> delete(Integer id);

    ResponseEntity<Page<SpotReviewModel>> findReviewsForSpot(PageRequest pageRequest, Integer spotId, String sortOption);

    ResponseEntity<SpotReviewModel> createSpotReview(SpotReviewCreateModel createRequest, Principal principal);

    ResponseEntity<SpotReviewModel> updateSpotReview(SpotReviewUpdateModel updateRequest, Principal principal);

    ResponseEntity<SpotReviewModel> deleteSpotReview(SpotReviewDeleteModel deleteRequest, Principal principal);
}
