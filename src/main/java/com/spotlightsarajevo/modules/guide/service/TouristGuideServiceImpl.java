package com.spotlightsarajevo.modules.guide.service;

import com.spotlightsarajevo.common.enums.ExceptionCodes;
import com.spotlightsarajevo.common.enums.ObjectType;
import com.spotlightsarajevo.common.exceptions.TouristGuideExceptions;
import com.spotlightsarajevo.common.utils.CommonFunctions;
import com.spotlightsarajevo.modules.guide.api.dto.*;
import com.spotlightsarajevo.modules.guide.domain.TouristGuideDAO;
import com.spotlightsarajevo.modules.guide.domain.TouristGuideSectionDAO;
import com.spotlightsarajevo.modules.guide.domain.entity.TouristGuideEntity;
import com.spotlightsarajevo.modules.guide.domain.entity.TouristGuideSectionEntity;
import com.spotlightsarajevo.modules.guide.mapper.TouristGuideMapper;
import com.spotlightsarajevo.modules.guide.utils.TouristGuideUtilities;
import com.spotlightsarajevo.modules.media.api.dto.MediaStoreCreateModel;
import com.spotlightsarajevo.modules.media.service.MediaService;
import com.spotlightsarajevo.modules.auth.domain.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TouristGuideServiceImpl implements TouristGuideService {
    TouristGuideDAO touristGuideDAO;
    TouristGuideSectionDAO touristGuideSectionDAO;
    TouristGuideMapper touristGuideMapper;
    TouristGuideUtilities guideUtilities;
    MediaService mediaService;
    CommonFunctions utils;

    @Override
    public ResponseEntity<List<TouristGuideShorthandModel>> findAllGuides() {
        List<TouristGuideEntity> entities = touristGuideDAO.findAll();

        for(TouristGuideEntity entity : entities){
            guideUtilities.setGuideCategoryNames(entity);
            mediaService.lookupThumbnailImage(entity, ObjectType.GUIDE_THUMBNAIL, entity.getId());
        }

        List<TouristGuideShorthandModel> models = touristGuideMapper.entitiesToShorthandDtos(entities);


        return ResponseEntity.status(200).body(models);
    }

    @Override
    public ResponseEntity<List<TouristGuideShorthandModel>> findByCategory(Integer categoryId) {
        if (categoryId == null) {
            throw new TouristGuideExceptions.TouristGuideRequestException(
                    ExceptionCodes.TouristGuideExceptionCodes.TOURIST_GUIDE_BAD_REQUEST
            );
        }

        List<TouristGuideEntity> entities = touristGuideDAO.findByCategoryId(categoryId);

        for (TouristGuideEntity entity : entities) {
            guideUtilities.setGuideCategoryNames(entity);
            mediaService.lookupThumbnailImage(entity, ObjectType.GUIDE_THUMBNAIL, entity.getId());
        }

        List<TouristGuideShorthandModel> models = touristGuideMapper.entitiesToShorthandDtos(entities);

        return ResponseEntity.ok(models);
    }

    @Override
    public ResponseEntity<TouristGuideOverviewModel> findGuideOverview(String slug) {
        if(slug == null) throw new TouristGuideExceptions.TouristGuideRequestException(ExceptionCodes.TouristGuideExceptionCodes.TOURIST_GUIDE_BAD_REQUEST);

        Optional<TouristGuideEntity> entityOptional = touristGuideDAO.findBySlug(slug);
        TouristGuideEntity entity = entityOptional.orElseThrow(
                () -> new TouristGuideExceptions.TouristGuideRequestException(ExceptionCodes.TouristGuideExceptionCodes.TOURIST_GUIDE_NOT_FOUND)
        );

        mediaService.lookupThumbnailImage(entity, ObjectType.GUIDE_THUMBNAIL, entity.getId());
        guideUtilities.setGuideCategoryNames(entity);

        TouristGuideOverviewModel overviewModel = touristGuideMapper.entityToOverviewDto(entity);

        List<TouristGuideSectionEntity> sectionEntities = touristGuideSectionDAO.findAllByGuideId(entity.getId());
        List<TouristGuideSectionModel> sectionModels = new ArrayList<>();


        for(TouristGuideSectionEntity section : sectionEntities){
            mediaService.lookupThumbnailImage(section, ObjectType.SECTION_GUIDE, section.getId());
            TouristGuideSectionModel model = touristGuideMapper.sectionEntityToDto(section);
            sectionModels.add(model);
        }

        overviewModel.setSections(sectionModels);

        return ResponseEntity.status(200).body(overviewModel);
    }

    @Override
    @Transactional
    public ResponseEntity<TouristGuideOverviewModel> create(TouristGuideCreateModel request, Principal principal) {
        if (request == null) throw new TouristGuideExceptions.TouristGuideRequestException(ExceptionCodes.TouristGuideExceptionCodes.TOURIST_GUIDE_C_BR);

        UserEntity user = utils.getUserFromPrincipal(principal);
        if (user == null || !user.getIsAdmin()) throw new TouristGuideExceptions.TouristGuideAuth(ExceptionCodes.TouristGuideExceptionCodes.TOURIST_GUIDE_C_AU);

        // Create the initial guide entity within the database
        String slug = utils.generateSlug(request.getGuideTitleEn());

        Optional<TouristGuideEntity> testEntity = touristGuideDAO.findBySlug(slug);
        if(testEntity.isPresent()) throw new TouristGuideExceptions.TouristGuideConflict(ExceptionCodes.TouristGuideExceptionCodes.TOURIST_GUIDE_C_SLUG);

        TouristGuideEntity guideEntity = touristGuideMapper.dtoToEntity(request);
        guideEntity.setSlug(slug);
        TouristGuideEntity storedEntity = touristGuideDAO.save(guideEntity);

        // Add the thumbnail image of the guide to the database
        MediaStoreCreateModel guideImageModel = new MediaStoreCreateModel(
                storedEntity.getId(),
                ObjectType.GUIDE_THUMBNAIL.toString(),
                request.getThumbnailImage(),
                "EMPTY FOR NOW",
                true,
                LocalDateTime.now(),
                user.getUsername()
        );
        System.out.println(guideImageModel);
        mediaService.create(guideImageModel);

        // Create the individual section objects and add their images to the database
        for (TouristGuideSectionCreateModel section : request.getSections()){
            TouristGuideSectionEntity sectionEntity = touristGuideMapper.dtoToSectionEntity(section);
            sectionEntity.setGuideId(storedEntity.getId());
            TouristGuideSectionEntity storedSectionEntity = touristGuideSectionDAO.save(sectionEntity);

            // Add the (thumbnail) image of the guide to the database
            MediaStoreCreateModel guideSectionImageModel = new MediaStoreCreateModel(
                    storedSectionEntity.getId(),
                    ObjectType.SECTION_GUIDE.toString(),
                    section.getThumbnailImage(),
                    "EMPTY FOR NOW",
                    true,
                    LocalDateTime.now(),
                    user.getUsername()
            );
            mediaService.create(guideSectionImageModel);
        }

        return ResponseEntity.status(200).body(touristGuideMapper.entityToOverviewDto(storedEntity));
    }
}
