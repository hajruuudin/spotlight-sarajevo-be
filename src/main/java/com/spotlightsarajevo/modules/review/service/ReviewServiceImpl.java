package com.spotlightsarajevo.modules.review.service;

import com.spotlightsarajevo.common.enums.ExceptionCodes;
import com.spotlightsarajevo.common.exceptions.SpotExceptions;
import com.spotlightsarajevo.common.specifications.SpotReviewSpecification;
import com.spotlightsarajevo.modules.review.domain.SpotReviewDAO;
import com.spotlightsarajevo.modules.review.mapper.SpotReviewMapper;
import com.spotlightsarajevo.modules.review.utils.ReviewUtilities;
import com.spotlightsarajevo.modules.review.api.dto.SpotReviewCreateModel;
import com.spotlightsarajevo.modules.review.api.dto.SpotReviewModel;
import com.spotlightsarajevo.modules.review.api.dto.SpotReviewUpdateModel;
import com.spotlightsarajevo.modules.review.domain.entity.SpotReviewEntity;
import com.spotlightsarajevo.modules.user.domain.UserDAO;
import com.spotlightsarajevo.modules.user.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    SpotReviewDAO spotReviewDAO;
    SpotReviewMapper spotReviewMapper;
    UserDAO userDAO;
    ReviewUtilities utils;

    /* ============================================================== */
    /* =================== DATA RETRIEVAL METHODS =================== */
    /* ============================================================== */

    @Override
    public ResponseEntity<Page<SpotReviewModel>> findReviewsForSpot(PageRequest pageRequest, Integer spotId, String sortOption) {
        if (spotId == null)
            throw new SpotExceptions.SpotSystemException(
                    ExceptionCodes.SpotExceptionCodes.INVALID_REQUEST_CONTENT);

        Specification<SpotReviewEntity> spec = Specification
                .allOf(SpotReviewSpecification.hasSpotId(spotId))
                .and(SpotReviewSpecification.withDynamicSorting(sortOption));

        Page<SpotReviewEntity> page = spotReviewDAO.findAll(spec, pageRequest);

        Page<SpotReviewModel> response = new PageImpl<>(
                spotReviewMapper.entitiesToDto(page.getContent()),
                pageRequest,
                page.getTotalElements()
        );

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SpotReviewModel> findUserReview(Integer spotId, Principal principal) {
        if(spotId == null)
            throw new SpotExceptions.SpotReviewException(ExceptionCodes.SpotExceptionCodes.INVALID_REQUEST_CONTENT);

        Optional<UserEntity> userEntity = userDAO.findBySysEmailOrGoogleEmail(principal.getName());

        if(userEntity.isPresent()){
            Optional<SpotReviewEntity> spotReviewEntity = spotReviewDAO.findBySpotIdAndUserId(spotId, userEntity.get().getId());

            return spotReviewEntity
                    .map(entity -> ResponseEntity.status(200).body(spotReviewMapper.entityToDto(entity)))
                    .orElseGet(() -> ResponseEntity.status(200).body(null));
        } else {
            throw new SpotExceptions.SpotReviewException(ExceptionCodes.SpotExceptionCodes.SPOT_UNAUTHORIZED_ACCESS);
        }
    }

    /* ============================================================== */
    /* ============== CREATE - UPDATE - DELETE METHODS ============== */
    /* ============================================================== */

    @Override
    public ResponseEntity<SpotReviewModel> createSpotReview(SpotReviewCreateModel dto, Principal principal) {
        Optional<UserEntity> user = userDAO.findBySysEmailOrGoogleEmail(principal.getName());

        if(user.isPresent()){
            SpotReviewEntity entity = spotReviewMapper.dtoToEntity(dto);
            entity.setUserId(user.get().getId());
            entity.setUsername(user.get().getUsername());
            entity.setCreated(LocalDate.now());

            SpotReviewEntity created = spotReviewDAO.save(entity);
            return ResponseEntity.ok(spotReviewMapper.entityToDto(created));
        } else {
            throw new SpotExceptions.SpotReviewException(ExceptionCodes.SpotExceptionCodes.INVALID_REQUEST_CONTENT);
        }
    }


    @Override
    public ResponseEntity<SpotReviewModel> updateSpotReview(SpotReviewUpdateModel dto, Principal principal) {
//        if (!utils.isAuthorisedUser(dto.getUserId(), principal)) {
//            throw new SpotExceptions.SpotUnauthorizedException(
//                    ExceptionCodes.SpotExceptionCodes.SPOT_UNAUTHORIZED_ACCESS);
//        }

        SpotReviewEntity entity = spotReviewDAO.findById(dto.getId())
                .orElseThrow(() ->
                        new SpotExceptions.SpotReviewException(
                                ExceptionCodes.SpotExceptionCodes.SPOT_REVIEW_NOT_FOUND));

        spotReviewMapper.updateEntityFromDto(dto, entity);
        entity.setModified(LocalDate.now());

        spotReviewDAO.save(entity);
        return ResponseEntity.ok(spotReviewMapper.entityToDto(entity));
    }

    @Override
    public ResponseEntity<SpotReviewModel> deleteSpotReview(Integer spotId, Integer reviewId, Principal principal) {
        if (spotId == null)
            throw new SpotExceptions.SpotSystemException(
                    ExceptionCodes.SpotExceptionCodes.EMPTY_SPOT_REQUEST_BODY);

        SpotReviewEntity review = spotReviewDAO.findById(reviewId)
                .orElseThrow(() ->
                        new SpotExceptions.SpotReviewException(
                                ExceptionCodes.SpotExceptionCodes.SPOT_REVIEW_NOT_FOUND));

        if (!review.getSpotId().equals(spotId))
            throw new SpotExceptions.SpotReviewException(
                    ExceptionCodes.SpotExceptionCodes.SPOT_REVIEW_CONFLICT);

        spotReviewDAO.delete(review);
        return ResponseEntity.ok(spotReviewMapper.entityToDto(review));
    }

}
