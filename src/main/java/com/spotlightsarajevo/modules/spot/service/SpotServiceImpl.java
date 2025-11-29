package com.spotlightsarajevo.modules.spot.service;

import com.spotlightsarajevo.common.specifications.SpotReviewSpecification;
import com.spotlightsarajevo.modules.spot.api.dto.*;
import com.spotlightsarajevo.modules.spot.domain.SpotDAO;
import com.spotlightsarajevo.modules.spot.domain.SpotReviewDAO;
import com.spotlightsarajevo.modules.spot.domain.entity.SpotEntity;
import com.spotlightsarajevo.modules.media.service.LookupImagesService;
import com.spotlightsarajevo.modules.spot.domain.entity.SpotReviewEntity;
import com.spotlightsarajevo.modules.spot.mapper.SpotMapper;
import com.spotlightsarajevo.common.enums.ExceptionCodes;
import com.spotlightsarajevo.common.enums.ObjectType;
import com.spotlightsarajevo.common.exceptions.SpotExceptions;
import com.spotlightsarajevo.common.utils.CommonFunctions;
import com.spotlightsarajevo.common.specifications.SpotSpecifications;
import com.spotlightsarajevo.modules.spot.mapper.SpotReviewMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@Service
public class SpotServiceImpl implements SpotService {

    private final SpotDAO spotDAO;
    private final SpotReviewDAO spotReviewDAO;
    private final SpotMapper spotMapper;
    private final SpotReviewMapper spotReviewMapper;
    private final LookupImagesService lookupImagesService;
    private final CommonFunctions utils;

    /* ---------------------------------------------
     *  SPOT LISTING
     * --------------------------------------------- */

    @Override
    public ResponseEntity<Page<SpotModel>> findAll(PageRequest pageRequest) {
        Page<SpotEntity> page = spotDAO.findAll(pageRequest);

        List<SpotModel> models = spotMapper.entitiesToDtos(page.getContent());

        Page<SpotModel> response = new PageImpl<>(models, pageRequest, page.getTotalElements());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Page<SpotShorthandModel>> findSpotsPaginated(PageRequest pageRequest, String searchTerm, String sortOption, List<Integer> categoryIds) {
        if (pageRequest == null)
            throw new SpotExceptions.SpotSystemException(ExceptionCodes.SpotExceptionCodes.INVALID_REQUEST_CONTENT);

        if (categoryIds == null)
            categoryIds = new ArrayList<>();

        Specification<SpotEntity> spec = Specification
                .allOf(SpotSpecifications.hasSearchTerm(searchTerm))
                .and(SpotSpecifications.hasCategories(categoryIds))
                .and(SpotSpecifications.withDynamicSorting(sortOption));

        Page<SpotEntity> page = spotDAO.findAll(spec, pageRequest);

        for (SpotEntity spot : page.getContent()) {
            utils.setSpotCategoriesAndRating(spot);
            utils.setSpotTags(spot);
            lookupImagesService.lookupThumbnailImage(spot, ObjectType.SPOT, spot.getId());
        }

        Page<SpotShorthandModel> response = new PageImpl<>(
                spotMapper.entitiesToShorthandDtos(page.getContent()),
                pageRequest,
                page.getTotalElements()
        );

        return ResponseEntity.ok(response);
    }

    /* ---------------------------------------------
     *  SPOT OVERVIEW
     * --------------------------------------------- */

    @Override
    public ResponseEntity<SpotOverviewModel> findSpotOverview(String slug) {
        if (slug == null || slug.isBlank()) {
            throw new SpotExceptions.SpotNotFoundException(
                    ExceptionCodes.SpotExceptionCodes.INVALID_REQUEST_CONTENT
            );
        }

        SpotEntity spot = spotDAO.findBySlug(slug)
                .orElseThrow(() ->
                        new SpotExceptions.SpotNotFoundException(
                                ExceptionCodes.SpotExceptionCodes.SPOT_NOT_FOUND));

        utils.setSpotCategoriesAndRating(spot);
        utils.setSpotTags(spot);
        utils.setSpotWorkHours(spot);

        SpotOverviewModel model = spotMapper.entityOverviewToDto(spot);

        lookupImagesService.lookupThumbnailImage(model, ObjectType.SPOT, spot.getId());
        lookupImagesService.lookupAllImages(model, ObjectType.SPOT, spot.getId());

        return ResponseEntity.ok(model);
    }

    /* ---------------------------------------------
     *  CREATE / UPDATE / DELETE SPOT
     * --------------------------------------------- */

    @Override
    public ResponseEntity<SpotModel> create(SpotCreateModel dto) {
        SpotEntity entity = spotMapper.dtoToEntity(dto);
        entity.setCreated(LocalDateTime.now());
        entity.setCreatedBy("Admin User");

        SpotEntity created = spotDAO.save(entity);

        return ResponseEntity.ok(spotMapper.entityToDto(created));
    }

    @Override
    public ResponseEntity<SpotModel> update(SpotUpdateModel dto) {
        SpotEntity entity = spotDAO.findById(dto.getId())
                .orElseThrow(() ->
                        new SpotExceptions.SpotNotFoundException(
                                ExceptionCodes.SpotExceptionCodes.SPOT_NOT_FOUND));

        spotMapper.updateEntityFromDto(dto, entity);
        entity.setModified(LocalDateTime.now());
        entity.setModifiedBy("Admin User");

        spotDAO.save(entity);
        return ResponseEntity.ok(spotMapper.entityToDto(entity));
    }

    @Override
    public ResponseEntity<SpotModel> delete(Integer id) {
        SpotEntity entity = spotDAO.findById(id)
                .orElseThrow(() ->
                        new SpotExceptions.SpotNotFoundException(
                                ExceptionCodes.SpotExceptionCodes.SPOT_NOT_FOUND));

        spotDAO.delete(entity);
        return ResponseEntity.ok(spotMapper.entityToDto(entity));
    }

    /* ---------------------------------------------
     *  SPOT REVIEWS
     * --------------------------------------------- */

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

    /* ---------------------------------------------
     *  CREATE REVIEW
     * --------------------------------------------- */

    @Override
    public ResponseEntity<SpotReviewModel> createSpotReview(SpotReviewCreateModel dto, Principal principal) {
        validateUser(dto.getUserId(), principal);

        SpotReviewEntity entity = spotReviewMapper.dtoToEntity(dto);
        entity.setCreated(LocalDate.now());

        SpotReviewEntity created = spotReviewDAO.save(entity);
        return ResponseEntity.ok(spotReviewMapper.entityToDto(created));
    }

    /* ---------------------------------------------
     *  UPDATE REVIEW
     * --------------------------------------------- */

    @Override
    public ResponseEntity<SpotReviewModel> updateSpotReview(SpotReviewUpdateModel dto, Principal principal) {
        validateUser(dto.getUserId(), principal);

        SpotReviewEntity entity = spotReviewDAO.findById(dto.getId())
                .orElseThrow(() ->
                        new SpotExceptions.SpotReviewException(
                                ExceptionCodes.SpotExceptionCodes.SPOT_REVIEW_NOT_FOUND));

        spotReviewMapper.updateEntityFromDto(dto, entity);
        entity.setModified(LocalDate.now());

        spotReviewDAO.save(entity);
        return ResponseEntity.ok(spotReviewMapper.entityToDto(entity));
    }

    /* ---------------------------------------------
     *  DELETE REVIEW
     * --------------------------------------------- */

    @Override
    public ResponseEntity<SpotReviewModel> deleteSpotReview(SpotReviewDeleteModel dto, Principal principal) {
        validateUser(dto.getUserId(), principal);

        if (dto.getSpotId() == null)
            throw new SpotExceptions.SpotSystemException(
                    ExceptionCodes.SpotExceptionCodes.EMPTY_SPOT_REQUEST_BODY);

        SpotReviewEntity review = spotReviewDAO.findById(dto.getId())
                .orElseThrow(() ->
                        new SpotExceptions.SpotReviewException(
                                ExceptionCodes.SpotExceptionCodes.SPOT_REVIEW_NOT_FOUND));

        if (!review.getSpotId().equals(dto.getSpotId()))
            throw new SpotExceptions.SpotReviewException(
                    ExceptionCodes.SpotExceptionCodes.SPOT_REVIEW_CONFLICT);

        spotReviewDAO.delete(review);
        return ResponseEntity.ok(spotReviewMapper.entityToDto(review));
    }

    /* ---------------------------------------------
     *  PRIVATE HELPERS
     * --------------------------------------------- */

    private void validateUser(Integer userId, Principal principal) {
        if (!utils.isAuthorisedUser(userId, principal)) {
            throw new SpotExceptions.SpotUnauthorizedException(
                    ExceptionCodes.SpotExceptionCodes.SPOT_UNAUTHORIZED_ACCESS);
        }
    }
}

