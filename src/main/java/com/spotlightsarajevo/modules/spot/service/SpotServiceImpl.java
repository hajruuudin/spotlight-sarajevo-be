package com.spotlightsarajevo.modules.spot.service;

import com.spotlightsarajevo.modules.spot.api.dto.*;
import com.spotlightsarajevo.modules.spot.domain.SpotDAO;
import com.spotlightsarajevo.modules.review.domain.SpotReviewDAO;
import com.spotlightsarajevo.modules.spot.domain.entity.SpotEntity;
import com.spotlightsarajevo.modules.media.service.LookupImagesService;
import com.spotlightsarajevo.modules.spot.mapper.SpotMapper;
import com.spotlightsarajevo.common.enums.ExceptionCodes;
import com.spotlightsarajevo.common.enums.ObjectType;
import com.spotlightsarajevo.common.exceptions.SpotExceptions;
import com.spotlightsarajevo.common.specifications.SpotSpecifications;
import com.spotlightsarajevo.modules.review.mapper.SpotReviewMapper;
import com.spotlightsarajevo.modules.spot.utils.SpotUtilities;
import com.spotlightsarajevo.modules.user.domain.UserDAO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@Service
public class SpotServiceImpl implements SpotService {

    private final SpotDAO spotDAO;
    private final UserDAO userDAO;
    private final SpotReviewDAO spotReviewDAO;
    private final SpotMapper spotMapper;
    private final SpotReviewMapper spotReviewMapper;
    private final LookupImagesService lookupImagesService;
    private final SpotUtilities utils;



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
}

