package ba.spotlightsarajevo.service.implementation.service;

import ba.spotlightsarajevo.dao.ReviewDAO;
import ba.spotlightsarajevo.dao.SpotReviewDAO;
import ba.spotlightsarajevo.dao.UserDAO;
import ba.spotlightsarajevo.dao.entities.ReviewEntity;
import ba.spotlightsarajevo.dao.entities.SpotReviewEntity;
import ba.spotlightsarajevo.dao.entities.UserEntity;
import ba.spotlightsarajevo.dao.models.review.ReviewCreate;
import ba.spotlightsarajevo.dao.models.review.ReviewModel;
import ba.spotlightsarajevo.service.definition.mapper.ReviewMapper;
import ba.spotlightsarajevo.service.definition.service.ReviewService;
import ba.spotlightsarajevo.utils.SSEntityRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    ReviewDAO reviewDAO;
    SpotReviewDAO spotReviewDAO;
    UserDAO userDAO;
    ReviewMapper reviewMapper;

    @Override
    public ResponseEntity<ReviewModel> addReviewForSpot(ReviewCreate request, Principal principal) {
        try{
            UserEntity currentUser = userDAO.findByEmail(principal.getName());

            if(currentUser == null){
                throw new IllegalAccessException("User not logged in, or not present!");
            } else {
                ReviewEntity savedReview = reviewMapper.dtoToEntity(request);
                savedReview.setUserId(currentUser.getId());

                reviewDAO.save(savedReview);

                SpotReviewEntity spotReviewEntity = new SpotReviewEntity();

                spotReviewEntity.setReviewId(savedReview.getId());
                spotReviewEntity.setSpotId(savedReview.getSpotId());

                spotReviewDAO.save(spotReviewEntity);

                return ResponseEntity.ok(reviewMapper.entityToDto(savedReview));
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Override
    public ResponseEntity<ReviewModel> getUserReview(SSEntityRequest<Integer> request, Principal principal) {
        try{
            UserEntity currentUser = userDAO.findByEmail(principal.getName());

            if(currentUser == null){
                throw new IllegalAccessException("User not logged in, or not present!");
            } else {
                ReviewEntity reviewEntity = reviewDAO.findBySpotIdAndUserId(request.getData(), currentUser.getId());

                return ResponseEntity.ok(reviewMapper.entityToDto(reviewEntity));
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Override
    public ResponseEntity<Boolean> deleteUserReview(SSEntityRequest<Integer> request, Principal principal) {
        try{
            UserEntity currentUser = userDAO.findByEmail(principal.getName());

            if(currentUser == null){
                throw new IllegalAccessException("User not logged in, or not present!");
            } else {
                ReviewEntity reviewEntity = reviewDAO.findBySpotIdAndUserId(request.getData(), currentUser.getId());
                reviewDAO.delete(reviewEntity);

                return ResponseEntity.ok(true);
            }
        } catch (Exception e){
            return ResponseEntity.status(400).body(false);
        }
    }

    @Override
    public ResponseEntity<Page<ReviewModel>> getAllReviews(PageRequest request, Integer spotId, Principal principal) {
        try{
            UserEntity currentUser = userDAO.findByEmail(principal.getName());

            if(currentUser == null){
                throw new IllegalAccessException("User not logged in, or not present!");
            } else {
                Page<ReviewEntity> reviewEntityPage = reviewDAO.findAllOthersBySpotId(request, spotId, currentUser.getId());

                List<ReviewModel> reviewModels = reviewMapper.etitiesToDtos(reviewEntityPage.getContent());

                Page<ReviewModel> response = new PageImpl<>(
                        reviewModels,
                        request,
                        reviewEntityPage.getTotalElements()
                );

                return  ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }
}
