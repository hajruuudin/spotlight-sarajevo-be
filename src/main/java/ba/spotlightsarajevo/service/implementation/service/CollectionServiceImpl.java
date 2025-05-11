package ba.spotlightsarajevo.service.implementation.service;

import ba.spotlightsarajevo.dao.*;
import ba.spotlightsarajevo.dao.entities.*;
import ba.spotlightsarajevo.dao.models.collection.CollectionCreateModel;
import ba.spotlightsarajevo.dao.models.collection.CollectionModel;
import ba.spotlightsarajevo.dao.models.collection.CollectionWithItemsModel;
import ba.spotlightsarajevo.dao.models.event.EventShorthand;
import ba.spotlightsarajevo.dao.models.spot.SpotShorthand;
import ba.spotlightsarajevo.enums.ObjectType;
import ba.spotlightsarajevo.service.definition.mapper.CollectionMapper;
import ba.spotlightsarajevo.service.definition.mapper.EventMapper;
import ba.spotlightsarajevo.service.definition.mapper.SpotMapper;
import ba.spotlightsarajevo.service.definition.service.CollectionService;
import ba.spotlightsarajevo.utils.ObjectShorthand;
import ba.spotlightsarajevo.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CollectionServiceImpl implements CollectionService {
    SpotDAO spotDAO;
    EventDAO eventDAO;
    UserCollectionDAO userCollectionDAO;
    UserDAO userDAO;
    CategoryDAO categoryDAO;
    SpotReviewStatsDAO spotReviewStatsDAO;
    SpotTagDAO spotTagDAO;
    EventTagDAO eventTagDAO;
    TagDAO tagDAO;
    CollectionDAO collectionDAO;
    CollectionEventDAO collectionEventDAO;
    CollectionSpotDAO collectionSpotDAO;
    SpotMapper spotMapper;
    EventMapper eventMapper;
    CollectionMapper collectionMapper;
    LookupImagesService lookupImagesService;
    ObjectUtils objectUtils;

    @Override
    public ResponseEntity<CollectionWithItemsModel> getCollectionByName(String collectionName, Principal principal) {
        if(principal == null) {
            return ResponseEntity.status(401).body(null);
        }

        UserEntity user = userDAO.findByEmail(principal.getName());

        if(user == null) {
            return ResponseEntity.status(400).body(null);
        }

        CollectionEntity collectionEntity = this.collectionDAO.findByColletionName(collectionName, user.getId());
        List<ObjectShorthand> collectionObjects = new ArrayList<>();

        if(collectionEntity == null){
            return ResponseEntity.status(404).body(null);
        } else {
            if(collectionEntity.getCollectionType().equals(ObjectType.SPOT)){
                List<CollectionSpotEntity> collectionSpotEntities = this.collectionSpotDAO.findAllByCollectionId(collectionEntity.getId());

                for(CollectionSpotEntity entity : collectionSpotEntities){
                    Optional<SpotEntity> spotEntity = this.spotDAO.findById(entity.getSpotId());

                    if(spotEntity.isPresent()){
                        objectUtils.setSpotInformation(categoryDAO, spotReviewStatsDAO, spotTagDAO, tagDAO, spotEntity.get());
                        SpotShorthand spotShorthand = spotMapper.entityToShorthandDto(spotEntity.get());

                        lookupImagesService.lookupThumbnailImage(spotShorthand, ObjectType.SPOT, spotShorthand.getId());
                        collectionObjects.add(spotShorthand);
                    } else {
                        continue;
                    }
                }
            } else if(collectionEntity.getCollectionType().equals(ObjectType.EVENT)) {
                List<CollectionEventEntity> collectionEventEntities = this.collectionEventDAO.findAllByCollectionId(collectionEntity.getId());

                for (CollectionEventEntity entity : collectionEventEntities) {
                    Optional<EventEntity> eventEntity = this.eventDAO.findById(entity.getEventId());

                    if (eventEntity.isPresent()) {
                        objectUtils.setEventInformation(categoryDAO, eventTagDAO, tagDAO, objectUtils.reverseDateFormatter, eventEntity.get());
                        EventShorthand eventShorthand = eventMapper.entityToShorthandDto(eventEntity.get());

                        lookupImagesService.lookupThumbnailImage(eventShorthand, ObjectType.EVENT, eventShorthand.getId());
                        collectionObjects.add(eventShorthand);
                    } else {
                        continue;
                    }
                }
            }
        }


        CollectionWithItemsModel collectionWithItemsModel = new CollectionWithItemsModel();
        collectionWithItemsModel.setId(collectionEntity.getId());
        collectionWithItemsModel.setCollectionName(collectionEntity.getCollectionName());
        collectionWithItemsModel.setCollectionType(collectionEntity.getCollectionType());
        collectionWithItemsModel.setCollectionItems(collectionObjects);

        return ResponseEntity.ok(collectionWithItemsModel);
    }

    @Override
    public ResponseEntity<CollectionModel> addCustomCollection(CollectionCreateModel request, Principal principal) {
        if(principal == null) {
            return ResponseEntity.status(401).body(null);
        }

        UserEntity user = userDAO.findByEmail(principal.getName());

        if(user == null) {
            return ResponseEntity.status(400).body(null);
        }

        CollectionEntity collectionEntity = new CollectionEntity();
        collectionEntity.setCollectionName(request.getCollectionName());
        collectionEntity.setCollectionType(request.getCollectionType());
        collectionEntity.setUserId(user.getId());

        try{
            collectionDAO.save(collectionEntity);
            return ResponseEntity.ok(collectionMapper.entityToDto(collectionEntity));
        } catch (Exception e){
            return ResponseEntity.status(400).body(null);
        }
    }
}
