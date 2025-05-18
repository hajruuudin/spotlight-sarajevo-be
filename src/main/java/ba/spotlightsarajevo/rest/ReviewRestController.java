package ba.spotlightsarajevo.rest;

import ba.spotlightsarajevo.dao.models.review.ReviewCreate;
import ba.spotlightsarajevo.dao.models.review.ReviewModel;
import ba.spotlightsarajevo.service.definition.service.ReviewService;
import ba.spotlightsarajevo.utils.SSEntityRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Tag(name = "Review", description = "Review API")
@RequestMapping(value = "/review")
@AllArgsConstructor
@RestController
public class ReviewRestController {
    ReviewService reviewService;

    @Operation(description = "Get all paginated reviews for a specific spot")
    @GetMapping
    public ResponseEntity<Page<ReviewModel>> getAllReviews(
            @RequestParam(value = "pageNumber", required = true) Integer pageNumber,
            @RequestParam(value = "pageSize", required = true) Integer pageSize,
            @RequestParam(value = "spotId", required = true) Integer spotId,
            Principal principal
            ){
        return reviewService.getAllReviews(PageRequest.of(pageNumber, pageSize), spotId, principal);
    }

    @Operation(description = "Get the users review of the spot. If it doesn't exist, just return an empty object")
    @GetMapping(value = "user")
    public ResponseEntity<ReviewModel> getUserReview(@RequestParam(value = "spotId", required = true) Integer spotId, Principal principal){
        return reviewService.getUserReview(new SSEntityRequest<>(spotId), principal);
    }

    @Operation(description = "Add a review for a specific spot")
    @PostMapping
    public ResponseEntity<ReviewModel> addReviewForSpot(@RequestBody ReviewCreate request, Principal principal){
        return this.reviewService.addReviewForSpot(request, principal);
    }

    @Operation(description = "Delete the users review from the database")
    @DeleteMapping
    public ResponseEntity<Boolean> deleteUserReview(@RequestParam(value = "spotId", required = true) Integer spotId, Principal principal){
        return this.reviewService.deleteUserReview(new SSEntityRequest<>(spotId), principal);
    }
}
