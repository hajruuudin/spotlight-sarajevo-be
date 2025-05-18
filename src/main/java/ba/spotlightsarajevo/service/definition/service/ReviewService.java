package ba.spotlightsarajevo.service.definition.service;

import ba.spotlightsarajevo.dao.models.review.ReviewCreate;
import ba.spotlightsarajevo.dao.models.review.ReviewModel;
import ba.spotlightsarajevo.utils.SSEntityRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface ReviewService {
    ResponseEntity<ReviewModel> addReviewForSpot(ReviewCreate request, Principal principal);
    ResponseEntity<ReviewModel> getUserReview(SSEntityRequest<Integer> request, Principal principal);
    ResponseEntity<Boolean> deleteUserReview(SSEntityRequest<Integer> request, Principal principal);
    ResponseEntity<Page<ReviewModel>> getAllReviews(PageRequest request, Integer spotId, Principal principal);
}
