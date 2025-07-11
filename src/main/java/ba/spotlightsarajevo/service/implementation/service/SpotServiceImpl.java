package ba.spotlightsarajevo.service.implementation.service;

import ba.spotlightsarajevo.dao.*;
import ba.spotlightsarajevo.dao.entities.*;
import ba.spotlightsarajevo.dao.models.spot.*;
import ba.spotlightsarajevo.enums.ObjectType;
import ba.spotlightsarajevo.service.definition.mapper.SpotMapper;
import ba.spotlightsarajevo.service.definition.service.SpotService;
import ba.spotlightsarajevo.service.definition.validator.SpotValidator;
import ba.spotlightsarajevo.utils.ObjectUtils;
import ba.spotlightsarajevo.utils.SSEntityRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.rmi.NoSuchObjectException;
import java.security.Principal;
import java.util.*;

@AllArgsConstructor
@Service
public class SpotServiceImpl implements SpotService {
    SpotDAO spotDAO;
    CategoryDAO categoryDAO;
    TagDAO tagDAO;
    UserDAO userDAO;
    ReviewDAO reviewDAO;
    SpotTagDAO spotTagDAO;
    SpotReviewStatsDAO spotReviewStatsDAO;
    SpotReviewDAO spotReviewDAO;
    SpotWorkHoursDAO spotWorkHoursDAO;
    SpotMapper spotMapper;
    SpotValidator spotValidator;
    LookupImagesService lookupImagesService;
    ObjectUtils objectUtils;

    @Override
    public ResponseEntity<SpotModel> create(SpotCreate request, Principal principal) {
        try {
            UserEntity adminUser = userDAO.findByEmail(principal.getName());

            if(!adminUser.getIsAdmin()){
                throw new IllegalAccessException();
            } else {
                SpotEntity newSpot = new SpotEntity();
                objectUtils.addSpotBase(request, newSpot, adminUser, categoryDAO);

                SpotEntity addedSpot = spotDAO.save(newSpot);

                objectUtils.addSpotTags(request, addedSpot, adminUser, tagDAO, spotTagDAO);
                objectUtils.addSpotWorkHours(request, addedSpot, spotWorkHoursDAO);

                return ResponseEntity.ok(spotMapper.entityToDto(addedSpot));
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Event Create ERROR");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("User cannot access this function");
        }
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
    public ResponseEntity<List<SpotLocationModel>> getLocationData(String search) {
        List<SpotEntity> entities = spotDAO.findAllSearched(search);

        List<SpotLocationModel> locationModels = new ArrayList<>();

        for(SpotEntity entity : entities){
            SpotLocationModel spotLocationModel = new SpotLocationModel();

            spotLocationModel.setSlug(entity.getSlug());
            spotLocationModel.setLattitude(entity.getLatitude());
            spotLocationModel.setLongitude(entity.getLongitude());
            spotLocationModel.setOfficialName(entity.getOfficialName());

            Optional<CategoryEntity> category = categoryDAO.findById(entity.getCategoryId());
            if(category.isPresent()){
                spotLocationModel.setCategoryName(category.get().getCategoryName());
            }

            lookupImagesService.lookupThumbnailImage(spotLocationModel, ObjectType.SPOT, entity.getId());

            locationModels.add(spotLocationModel);
        }

        return ResponseEntity.ok(locationModels);
    }

    @Override
    public ResponseEntity<Page<SpotShorthand>> getSpotsShorthand(PageRequest request, String search, String sort, List<Integer> categoryIds) {
        if (categoryIds != null && categoryIds.isEmpty()) {
            categoryIds = null;
        }

        Page<SpotEntity> pagedSpotShorthandResponse = spotDAO.findAll(request, search, categoryIds, sort);
        List<SpotEntity> spotEntities = pagedSpotShorthandResponse.getContent();


        for(SpotEntity entity : spotEntities){
            objectUtils.setSpotShorthandInformation(categoryDAO, spotReviewStatsDAO, spotTagDAO, tagDAO, entity);
        }

        List<SpotShorthand> spotShorthandsList = spotMapper.entitiesToShorthandDtos(pagedSpotShorthandResponse.getContent());
        
        for(SpotShorthand spot : spotShorthandsList){
            lookupImagesService.lookupThumbnailImage(spot, ObjectType.SPOT, spot.getId());
        }

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

        objectUtils.setSpotShorthandInformation(categoryDAO, spotReviewStatsDAO, spotTagDAO, tagDAO, entity);


        SpotShorthand response = spotMapper.entityToShorthandDto(entity);
        lookupImagesService.lookupThumbnailImage(response, ObjectType.SPOT, response.getId());

        return ResponseEntity.status(200).body(response);
    }

    @Override
    public ResponseEntity<Page<SpotShorthand>> getCategorisedSpots(PageRequest request, Integer categoryId) {
        Page<SpotEntity> pagedSpotEntities = spotDAO.findAllByCategoryId(request, categoryId);

        List<SpotEntity> spotEntities = pagedSpotEntities.getContent();

        for(SpotEntity entity : spotEntities){
            objectUtils.setSpotShorthandInformation(categoryDAO, spotReviewStatsDAO, spotTagDAO, tagDAO, entity);
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
    public ResponseEntity<SpotModel> getSpotOverviewBySlug(SSEntityRequest<String> request) {
        try{
            String slug = request.getData();

            if(slug.isEmpty()){
                throw new IllegalArgumentException("Slug of the spot not found!");
            } else {
                SpotEntity spotEntity = spotDAO.findBySlug(slug);

                objectUtils.setSpotFullInformation(
                        categoryDAO,
                        spotReviewStatsDAO,
                        spotReviewDAO,
                        spotTagDAO,
                        spotWorkHoursDAO,
                        tagDAO,
                        reviewDAO,
                        spotEntity
                );

                SpotModel spotModel = spotMapper.entityToDto(spotEntity);

                lookupImagesService.lookupThumbnailImage(spotModel, ObjectType.SPOT, spotModel.getId());

                return ResponseEntity.ok(spotModel);
            }
        } catch (Exception e){
            return ResponseEntity.status(400).body(null);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<SpotModel> updateSpot(SpotUpdate request, Principal principal) {
        try {
            UserEntity adminUser = userDAO.findByEmail(principal.getName());

            if(!adminUser.getIsAdmin()){
                throw new IllegalAccessException();
            } else {
                Optional<SpotEntity> existingSpot = spotDAO.findById(request.getId());

                if(!existingSpot.isPresent()){
                    throw new NoSuchObjectException("");
                } else {
                    objectUtils.formatSpotUpdate
                            (request,
                            existingSpot.get(),
                            adminUser,
                            categoryDAO,
                            tagDAO,
                            spotTagDAO,
                            spotWorkHoursDAO
                    );

                    SpotEntity updatedSpot = spotDAO.save(existingSpot.get());

                    return ResponseEntity.ok(spotMapper.entityToDto(updatedSpot));
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Spot Update ERROR");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("User cannot access this function");
        } catch (NoSuchObjectException e) {
            throw new RuntimeException("No Spot Object Found");
        }
    }

}
