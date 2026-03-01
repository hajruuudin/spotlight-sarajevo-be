package com.spotlightsarajevo.modules.spot.service;

import com.spotlightsarajevo.common.exceptions.EventExceptions;
import com.spotlightsarajevo.common.utils.CommonFunctions;
import com.spotlightsarajevo.modules.auth.domain.entity.UserEntity;
import com.spotlightsarajevo.modules.event.domain.entity.EventEntity;
import com.spotlightsarajevo.modules.media.api.dto.MediaStoreCreateModel;
import com.spotlightsarajevo.modules.media.domain.MediaStoreDAO;
import com.spotlightsarajevo.modules.media.domain.entity.MediaStoreEntity;
import com.spotlightsarajevo.modules.media.service.MediaService;
import com.spotlightsarajevo.modules.media.utils.ImageBBUtils;
import com.spotlightsarajevo.modules.spot.api.dto.*;
import com.spotlightsarajevo.modules.spot.domain.SpotDAO;
import com.spotlightsarajevo.modules.spot.domain.SpotTagsDAO;
import com.spotlightsarajevo.modules.spot.domain.SpotWorkHoursDAO;
import com.spotlightsarajevo.modules.spot.domain.entity.SpotTagsEntity;
import com.spotlightsarajevo.modules.spot.domain.entity.SpotWorkHoursEntity;
import com.spotlightsarajevo.modules.review.domain.SpotReviewDAO;
import com.spotlightsarajevo.modules.spot.domain.entity.SpotEntity;
import com.spotlightsarajevo.modules.spot.mapper.SpotMapper;
import com.spotlightsarajevo.modules.spot.mapper.SpotWorkHoursMapper;
import com.spotlightsarajevo.common.enums.ExceptionCodes;
import com.spotlightsarajevo.common.enums.ObjectType;
import com.spotlightsarajevo.common.exceptions.SpotExceptions;
import com.spotlightsarajevo.common.specifications.SpotSpecifications;
import com.spotlightsarajevo.modules.review.mapper.SpotReviewMapper;
import com.spotlightsarajevo.modules.spot.utils.SpotUtilities;
import com.spotlightsarajevo.modules.auth.domain.UserDAO;
import com.spotlightsarajevo.modules.auth.domain.UserVisitedSpotsDAO;
import com.spotlightsarajevo.modules.auth.domain.entity.UserVisitedSpotsEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@Service
public class SpotServiceImpl implements SpotService {

    private final SpotDAO spotDAO;
    private final UserDAO userDAO;
    private final UserVisitedSpotsDAO userVisitedSpotsDAO;
    private final SpotReviewDAO spotReviewDAO;
    private final SpotTagsDAO spotTagsDAO;
    private final SpotWorkHoursDAO spotWorkHoursDAO;
    private final MediaStoreDAO mediaStoreDAO;
    private final SpotMapper spotMapper;
    private final SpotWorkHoursMapper spotWorkHoursMapper;
    private final SpotReviewMapper spotReviewMapper;
    private final MediaService mediaService;
    private final SpotUtilities utils;
    private final CommonFunctions commonFunctions;
    private final ImageBBUtils imageBBUtils;

    @Override
    public ResponseEntity<Page<SpotModel>> findAll(PageRequest pageRequest) {
        Page<SpotEntity> page = spotDAO.findAll(pageRequest);

        List<SpotModel> models = spotMapper.entitiesToDtos(page.getContent());

        Page<SpotModel> response = new PageImpl<>(models, pageRequest, page.getTotalElements());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Page<SpotShorthandModel>> findSpotsPaginated(PageRequest pageRequest, String searchTerm, String sortOption, List<Integer> categoryIds, BigDecimal userLatitude, BigDecimal userLongitude, Boolean excludeVisited, Principal principal) {
        if (pageRequest == null)
            throw new SpotExceptions.SpotSystemException(ExceptionCodes.SpotExceptionCodes.INVALID_REQUEST_CONTENT);

        if (categoryIds == null)
            categoryIds = new ArrayList<>();

        List<Integer> visitedSpotIds = new ArrayList<>();
        if (Boolean.TRUE.equals(excludeVisited) && principal != null) {
            UserEntity user = commonFunctions.getUserFromPrincipal(principal);
            visitedSpotIds = userVisitedSpotsDAO.findAllByUserId(user.getId())
                    .stream()
                    .map(entity -> entity.getSpotId())
                    .toList();
        }

        Specification<SpotEntity> spec = Specification
                .allOf(SpotSpecifications.hasSearchTerm(searchTerm))
                .and(SpotSpecifications.hasCategories(categoryIds))
                .and(SpotSpecifications.excludeVisitedSpots(visitedSpotIds))
                .and(SpotSpecifications.withDynamicSorting(sortOption, userLatitude, userLongitude));

        Page<SpotEntity> page = spotDAO.findAll(spec, pageRequest);

        for (SpotEntity spot : page.getContent()) {
            utils.setSpotCategoriesAndRating(spot);
            utils.setSpotTags(spot);
            mediaService.lookupThumbnailImage(spot, ObjectType.SPOT, spot.getId());
        }

        Page<SpotShorthandModel> response = new PageImpl<>(
                spotMapper.entitiesToShorthandDtos(page.getContent()),
                pageRequest,
                page.getTotalElements()
        );

        return ResponseEntity.ok(response);
    }


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

        mediaService.lookupThumbnailImage(model, ObjectType.SPOT, spot.getId());
        mediaService.lookupAllImages(model, ObjectType.SPOT, spot.getId());

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
    @Transactional
    public ResponseEntity<SpotModel> update(SpotUpdateModel dto, Principal principal) {
        // Security checks
        if (principal == null) {
            throw new SpotExceptions.SpotUnauthorizedException(ExceptionCodes.SpotExceptionCodes.SPOT_UNAUTHORIZED_ACCESS);
        }

        UserEntity user = commonFunctions.getUserFromPrincipal(principal);
        if (!user.getIsAdmin()) {
            throw new SpotExceptions.SpotUnauthorizedException(ExceptionCodes.SpotExceptionCodes.SPOT_UNAUTHORIZED_ACCESS);
        }

        if (dto == null) {
            throw new SpotExceptions.SpotSystemException(ExceptionCodes.SpotExceptionCodes.INVALID_REQUEST_CONTENT);
        }

        // Retrieve existing spot
        SpotEntity entity = spotDAO.findById(dto.getId())
                .orElseThrow(() ->
                        new SpotExceptions.SpotNotFoundException(
                                ExceptionCodes.SpotExceptionCodes.SPOT_NOT_FOUND));

        // Update basic fields via mapper
        spotMapper.updateEntityFromDto(dto, entity);
        entity.setModified(LocalDateTime.now());
        entity.setModifiedBy(user.getUsername());

        // Handle tags: delete all existing tags and add new ones
        if (dto.getTagIds() != null) {
            spotTagsDAO.deleteAllBySpotId(entity.getId());

            List<SpotTagsEntity> newTags = new ArrayList<>();
            for (Integer tagId : dto.getTagIds()) {
                SpotTagsEntity spotTag = new SpotTagsEntity();
                spotTag.setSpotId(entity.getId());
                spotTag.setTagId(tagId);
                newTags.add(spotTag);
            }
            if (!newTags.isEmpty()) {
                spotTagsDAO.saveAllAndFlush(newTags);
            }
        }

        // Handle work hours: delete all existing work hours and add new ones
        if (dto.getWorkHoursModel() != null) {
            spotWorkHoursDAO.deleteAllBySpotId(entity.getId());

            List<SpotWorkHoursEntity> newWorkHours = new ArrayList<>();
            for (SpotWorkHoursModel workHoursModel : dto.getWorkHoursModel()) {
                SpotWorkHoursEntity workHoursEntity = spotWorkHoursMapper.dtoToEntity(workHoursModel);
                workHoursEntity.setSpotId(entity.getId());
                newWorkHours.add(workHoursEntity);
            }
            if (!newWorkHours.isEmpty()) {
                spotWorkHoursDAO.saveAllAndFlush(newWorkHours);
            }
        }

        // Handle thumbnail image: if new one provided, delete old from ImageBB and database, then add new
        if (dto.getNewThumbnailImage() != null) {
            // Get existing thumbnail to delete from ImageBB
            MediaStoreEntity existingThumbnail = mediaStoreDAO.findThumbnailByItemIdAndItemCategory(entity.getId(), ObjectType.SPOT);
            if (existingThumbnail != null && existingThumbnail.getImageDeleteUrl() != null) {
                imageBBUtils.deleteImage(existingThumbnail.getImageDeleteUrl());
            }

            // Delete existing thumbnail from database
            mediaStoreDAO.deleteByItemIdAndItemCategoryAndIsThumbnailTrue(entity.getId(), ObjectType.SPOT);

            // Add new thumbnail
            MediaStoreCreateModel thumbnailModel = dto.getNewThumbnailImage();
            thumbnailModel.setItemId(entity.getId());
            thumbnailModel.setItemCategory(ObjectType.SPOT.toString());
            thumbnailModel.setIsThumbnail(true);
            thumbnailModel.setCreated(LocalDateTime.now());
            thumbnailModel.setCreatedBy(user.getUsername());
            mediaService.create(thumbnailModel);
        }

        // Handle new images to add
        if (dto.getToAddImages() != null && !dto.getToAddImages().isEmpty()) {
            for (MediaStoreCreateModel imageModel : dto.getToAddImages()) {
                imageModel.setItemId(entity.getId());
                imageModel.setItemCategory(ObjectType.SPOT.toString());
                imageModel.setIsThumbnail(false);
                imageModel.setCreated(LocalDateTime.now());
                imageModel.setCreatedBy(user.getUsername());
                mediaService.create(imageModel);
            }
        }

        // Handle images to remove - delete from ImageBB first, then from database
        if (dto.getToRemoveImages() != null && !dto.getToRemoveImages().isEmpty()) {
            for (Integer imageId : dto.getToRemoveImages()) {
                // Get the image entity to retrieve the delete URL
                Optional<MediaStoreEntity> imageEntity = mediaStoreDAO.findById(imageId);
                if (imageEntity.isPresent()) {
                    // Delete from ImageBB
                    String deleteUrl = imageEntity.get().getImageDeleteUrl();
                    if (deleteUrl != null && !deleteUrl.isBlank()) {
                        imageBBUtils.deleteImage(deleteUrl);
                    }
                    // Delete from database
                    mediaStoreDAO.deleteById(imageId);
                }
            }
        }

        // Save the updated entity
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

    @Override
    public ResponseEntity<Boolean> getSpotVisitedStatus(Integer spotId, Principal principal) {
        if (spotId == null ) throw new SpotExceptions.SpotNotFoundException(ExceptionCodes.SpotExceptionCodes.SPOT_NOT_FOUND);
        if (principal == null) throw new EventExceptions.EventUnauthorizedException(ExceptionCodes.EventExceptionCodes.EVENT_UNAUTHORIZED_ACCESS);

        Optional<SpotEntity> spotEntity = spotDAO.findById(spotId);
        UserEntity userEntity = commonFunctions.getUserFromPrincipal(principal);

        if(spotEntity.isEmpty()){
            throw new SpotExceptions.SpotNotFoundException(ExceptionCodes.SpotExceptionCodes.SPOT_NOT_FOUND);
        }

        boolean isAttended = utils.checkIfUserVisitedSpot(spotEntity.get().getId(), userEntity.getId());
        return ResponseEntity.status(200).body(isAttended);
    }

    @Override
    public ResponseEntity<List<SpotMapModel>> findSpotsForMap(String searchTerm, List<Integer> categoryIds) {
        if (categoryIds == null)
            categoryIds = new ArrayList<>();

        Specification<SpotEntity> spec = Specification
                .allOf(SpotSpecifications.hasSearchTerm(searchTerm))
                .and(SpotSpecifications.hasCategories(categoryIds));

        List<SpotEntity> spots = spotDAO.findAll(spec);

        for (SpotEntity spot : spots) {
            utils.setSpotCategoriesAndRating(spot);
            mediaService.lookupThumbnailImage(spot, ObjectType.SPOT, spot.getId());
        }

        List<SpotMapModel> response = spotMapper.entitiesToMapDtos(spots);

        return ResponseEntity.ok(response);
    }
}

