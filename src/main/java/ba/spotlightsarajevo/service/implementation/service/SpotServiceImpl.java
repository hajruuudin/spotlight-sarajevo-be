package ba.spotlightsarajevo.service.implementation.service;

import ba.spotlightsarajevo.dao.*;
import ba.spotlightsarajevo.dao.entities.*;
import ba.spotlightsarajevo.dao.models.category.CategoryModel;
import ba.spotlightsarajevo.dao.models.spot.SpotCreate;
import ba.spotlightsarajevo.dao.models.spot.SpotModel;
import ba.spotlightsarajevo.dao.models.spot.SpotShorthand;
import ba.spotlightsarajevo.enums.ObjectType;
import ba.spotlightsarajevo.service.definition.mapper.SpotMapper;
import ba.spotlightsarajevo.service.definition.service.SpotService;
import ba.spotlightsarajevo.service.definition.validator.SpotValidator;
import ba.spotlightsarajevo.utils.SSEntityRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class SpotServiceImpl implements SpotService {
    SpotDAO spotDAO;
    CategoryDAO categoryDAO;
    TagDAO tagDAO;
    SpotTagDAO spotTagDAO;
    SpotReviewStatsDAO spotReviewStatsDAO;
    SpotWorkHoursDAO spotWorkHoursDAO;
    SpotMapper spotMapper;
    SpotValidator spotValidator;
    LookupImagesService lookupImagesService;

    @Override
    public ResponseEntity<SpotModel> create(SSEntityRequest<SpotCreate> request) {
        SpotCreate spotCreate = request.getData();
        spotValidator.validateCreateRequest(spotCreate);

        SpotEntity newEntity = spotMapper.dtoToEntity(spotCreate);
        spotDAO.save(newEntity);

        return ResponseEntity.ok(spotMapper.entityToDto(newEntity));
    }

    @Override
    public ResponseEntity<Page<SpotModel>> getSpotsPaginated(PageRequest pageRequest) {
        Page<SpotEntity> pagedSpotResponse = spotDAO.findAll(pageRequest);

        List<SpotModel> spotModelList = spotMapper.entitiesToDtos(pagedSpotResponse.getContent());

        for(SpotModel spot : spotModelList){
            lookupImagesService.lookupThumbnailImage(spot, ObjectType.SPOT, spot.getId());
        }

        Page<SpotModel> spotResponse = new PageImpl<>(
                spotModelList,
                pageRequest,
                pagedSpotResponse.getTotalElements()
        );

        return ResponseEntity.ok(spotResponse);
    }

    @Override
    public ResponseEntity<Page<SpotShorthand>> getSpotsShorthand(PageRequest request, String search, String sort, List<Integer> categoryIds) {
        if (categoryIds != null && categoryIds.isEmpty()) {
            categoryIds = null;
        }

        Page<SpotEntity> pagedSpotShorthandResponse = spotDAO.findAll(request, search, categoryIds, sort);
        List<SpotEntity> spotEntities = pagedSpotShorthandResponse.getContent();


        for(SpotEntity entity : spotEntities){

            /* SETTING THE SPOT CATEGORIES */
            Optional<CategoryEntity> categoryEntity = categoryDAO.findById(entity.getCategoryId());
            categoryEntity.ifPresent(category -> entity.setCategoryName(category.getCategoryName()));

            /* SETTING THE SPOT REVIEW */
            Optional<SpotReviewStatsEntity> spotReviewStatsEntity = spotReviewStatsDAO.findById(entity.getId());
            spotReviewStatsEntity.ifPresent(reviewStatsEntity -> entity.setRating(reviewStatsEntity.getCombinedRating()));

            /* SETTING THE SPOT TAGS */
            List<SpotTagEntity> spotTags = spotTagDAO.findAllTagsById(entity.getId());
            List<String> tagNames = new ArrayList<>();

            for(SpotTagEntity spotTag : spotTags){
                Optional<TagEntity> tag = tagDAO.findById(spotTag.getTagId());
                tag.ifPresent(tagEntity -> tagNames.add(tagEntity.getTagName()));
            }

            entity.setTagNames(tagNames);
        }

        List<SpotShorthand> spotShorthandsList = spotMapper.entitiesToShorthandDtos(pagedSpotShorthandResponse.getContent());


        for(SpotShorthand spot : spotShorthandsList){
            lookupImagesService.lookupThumbnailImage(spot, ObjectType.SPOT, spot.getId());
        }

//        /* SORTING BASED ON THE OPTIONS */
//        if(sort != null) {
//            if (sort.equals("alphabetical") && spotShorthandsList.size() >= 2) {
//                spotShorthandsList.sort(Comparator.comparing(SpotShorthand::getOfficialName));
//            } else if (sort.equals("rating") && spotShorthandsList.size() >= 2) {
//                spotShorthandsList.sort(Comparator.comparing(SpotShorthand::getRating).reversed());
//            }
//        }

        Page<SpotShorthand> spotResponse = new PageImpl<>(
                spotShorthandsList,
                request,
                pagedSpotShorthandResponse.getTotalElements()
        );

        return ResponseEntity.ok(spotResponse);
    }

    @Override
    public ResponseEntity<SpotShorthand> getSpotHeadline() {
        Random rand = new Random();
        List<SpotEntity> entities = spotDAO.findAll();
        int totalItems = entities.size();

        SpotEntity entity = entities.get(rand.nextInt(totalItems));

        /* SETTING THE CATEGORY */
        Optional<CategoryEntity> spotCategory = categoryDAO.findById(entity.getCategoryId());
        spotCategory.ifPresent(categoryEntity -> entity.setCategoryName(categoryEntity.getCategoryName()));

        /* SETTING THE TAGS */
        List<SpotTagEntity> spotTags = spotTagDAO.findAllTagsById(entity.getId());
        List<String> tagNames = new ArrayList<>();

        for(SpotTagEntity spotTag : spotTags){
            Optional<TagEntity> tag = tagDAO.findById(spotTag.getTagId());
            tag.ifPresent(tagEntity -> tagNames.add(tagEntity.getTagName()));
        }

        entity.setTagNames(tagNames);

        SpotShorthand response = spotMapper.entityToShorthandDto(entity);
        lookupImagesService.lookupThumbnailImage(response, ObjectType.SPOT, response.getId());

        return ResponseEntity.status(200).body(response);
    }

    @Override
    public ResponseEntity<Page<SpotShorthand>> getCategorisedSpots(PageRequest request, Integer categoryId) {
        Page<SpotEntity> pagedSpotEntities = spotDAO.findAllByCategoryId(request, categoryId);

        List<SpotEntity> spotEntities = pagedSpotEntities.getContent();

        for(SpotEntity entity : spotEntities){

            /* SETTING THE SPOT CATEGORIES */
            Optional<CategoryEntity> categoryEntity = categoryDAO.findById(entity.getCategoryId());
            categoryEntity.ifPresent(category -> entity.setCategoryName(category.getCategoryName()));

            /* SETTING THE SPOT REVIEW */
            Optional<SpotReviewStatsEntity> spotReviewStatsEntity = spotReviewStatsDAO.findById(entity.getId());
            spotReviewStatsEntity.ifPresent(reviewStatsEntity -> entity.setRating(reviewStatsEntity.getCombinedRating()));

            /* SETTING THE SPOT TAGS */
            List<SpotTagEntity> spotTags = spotTagDAO.findAllTagsById(entity.getId());
            List<String> tagNames = new ArrayList<>();

            for(SpotTagEntity spotTag : spotTags){
                Optional<TagEntity> tag = tagDAO.findById(spotTag.getTagId());
                tag.ifPresent(tagEntity -> tagNames.add(tagEntity.getTagName()));
            }

            entity.setTagNames(tagNames);
        }

        List<SpotShorthand> spotShorthandsList = spotMapper.entitiesToShorthandDtos(pagedSpotEntities.getContent());


        for(SpotShorthand spot : spotShorthandsList){
            lookupImagesService.lookupThumbnailImage(spot, ObjectType.SPOT, spot.getId());
        }

        Page<SpotShorthand> spotResponse = new PageImpl<>(
                spotShorthandsList,
                request,
                pagedSpotEntities.getTotalElements()
        );

        return ResponseEntity.ok(spotResponse);
    }

    @Override
    public ResponseEntity<SpotModel> findBySlug(SSEntityRequest<String> request) {
        return null;
    }
}
