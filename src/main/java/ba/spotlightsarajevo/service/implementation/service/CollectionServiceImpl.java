package ba.spotlightsarajevo.service.implementation.service;

import ba.spotlightsarajevo.dao.*;
import ba.spotlightsarajevo.dao.entities.*;
import ba.spotlightsarajevo.dao.models.collection.CollectionCreateModel;
import ba.spotlightsarajevo.dao.models.collection.CollectionModel;
import ba.spotlightsarajevo.dao.models.collection.CollectionStatus;
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

import java.rmi.NoSuchObjectException;
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
                        objectUtils.setSpotShorthandInformation(categoryDAO, spotReviewStatsDAO, spotTagDAO, tagDAO, spotEntity.get());
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
                        objectUtils.setEventInformation(categoryDAO, eventTagDAO, tagDAO, eventEntity.get());
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

    @Override
    public ResponseEntity<List<CollectionStatus>> getCollectionsWithItemPresence(Integer objectId, String objectType, Principal principal) {
        try{
            UserEntity userEntity = userDAO.findByEmail(principal.getName());

            if(userEntity == null){
                throw new IllegalAccessException("Access denied!");
            } else {
                List<CollectionEntity> userCollections = collectionDAO.findByUserIdAndCollectionType(userEntity.getId(), ObjectType.valueOf(objectType));
                List<CollectionStatus> collectionStatuses = new ArrayList<>();

                for(CollectionEntity collection : userCollections){
                    CollectionStatus collectionStatus = new CollectionStatus();
                    collectionStatus.setCollectionName(collection.getCollectionName());

                    if(objectType.equals("SPOT")){
                        Optional<CollectionSpotEntity> spotEntity = collectionSpotDAO.findByCollectionIdAndSpotId(collection.getId(), objectId);

                        if(spotEntity.isPresent()){
                            collectionStatus.setItemStatus(true);
                        } else {
                            collectionStatus.setItemStatus(false);
                        }

                        collectionStatuses.add(collectionStatus);
                    } else if (objectType.equals("EVENT")){
                        Optional<CollectionEventEntity> eventEntity = collectionEventDAO.findByCollectionIdAndEventId(collection.getId(), objectId);

                        if(eventEntity.isPresent()){
                            collectionStatus.setItemStatus(true);
                        } else {
                            collectionStatus.setItemStatus(false);
                        }

                        collectionStatuses.add(collectionStatus);
                    } else {
                        throw new IllegalArgumentException("Invalid object type");
                    }
                }

                return ResponseEntity.ok(collectionStatuses);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(null);
        }
    }

    @Override
    public ResponseEntity<List<CollectionModel>> getAllCustomCollections(Principal principal) {
        try{
            UserEntity currentUser = userDAO.findByEmail(principal.getName());

            if(currentUser == null){
                throw new IllegalAccessException("User not found or access denied");
            } else {
                List<CollectionEntity> collectionEntities = collectionDAO.findCustomCollectionsByUserId(currentUser.getId());

                List<CollectionModel> collectionModels = collectionMapper.entitiesToDtos(collectionEntities);

                return ResponseEntity.ok(collectionModels);
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Override
    public ResponseEntity<CollectionStatus> addObjectToCollection(String collectionName, Integer objectId, Principal principal) {
        try{
            UserEntity currentUser = userDAO.findByEmail(principal.getName());

            if(currentUser == null){
                throw new IllegalAccessException("User not present/logged in!");
            } else {
                CollectionEntity collectionEntity = collectionDAO.findByUserIdAndCollectionName(currentUser.getId(), collectionName);

                if(collectionEntity == null){
                    throw new NoSuchObjectException("Collection does not exist for given user and name");
                } else {
                    ObjectType collectionType = collectionEntity.getCollectionType();

                    if(collectionType.equals(ObjectType.SPOT)){
                        CollectionSpotEntity collectionSpotEntity = new CollectionSpotEntity();

                        collectionSpotEntity.setCollectionId(collectionEntity.getId());
                        collectionSpotEntity.setSpotId(objectId);

                        collectionSpotDAO.save(collectionSpotEntity);

                        CollectionStatus collectionStatus = new CollectionStatus();
                        collectionStatus.setItemStatus(true);
                        collectionStatus.setCollectionName(collectionName);

                        return ResponseEntity.ok(collectionStatus);
                    } else if (collectionType.equals(ObjectType.EVENT)){
                        CollectionEventEntity collectionEventEntity = new CollectionEventEntity();

                        collectionEventEntity.setCollectionId(collectionEntity.getId());
                        collectionEventEntity.setEventId(objectId);

                        collectionEventDAO.save(collectionEventEntity);

                        CollectionStatus collectionStatus = new CollectionStatus();
                        collectionStatus.setItemStatus(true);
                        collectionStatus.setCollectionName(collectionName);

                        return ResponseEntity.ok(collectionStatus);
                    } else {
                        throw new IllegalArgumentException("Collection type must be SPOT or EVENT");
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(400).body(null);
        }
    }

    @Override
    public ResponseEntity<CollectionStatus> removeObjectToCollection(String collectionName, Integer objectId, Principal principal) {
        try{
            UserEntity currentUser = userDAO.findByEmail(principal.getName());

            if(currentUser == null){
                throw new IllegalAccessException("User not present/logged in!");
            } else {
                CollectionEntity collectionEntity = collectionDAO.findByUserIdAndCollectionName(currentUser.getId(), collectionName);

                if(collectionEntity == null){
                    throw new NoSuchObjectException("Collection does not exist for given user and name");
                } else {
                    ObjectType collectionType = collectionEntity.getCollectionType();

                    if(collectionType.equals(ObjectType.SPOT)){
                        Optional<CollectionSpotEntity> collectionSpotEntity = collectionSpotDAO.findByCollectionIdAndSpotId(collectionEntity.getId(), objectId);

                        if(collectionSpotEntity.isPresent()){
                            collectionSpotDAO.delete(collectionSpotEntity.get());
                        } else {
                            throw new IllegalStateException("Object isn't present in the collection");
                        }

                        CollectionStatus collectionStatus = new CollectionStatus();
                        collectionStatus.setItemStatus(false);
                        collectionStatus.setCollectionName(collectionName);

                        return ResponseEntity.ok(collectionStatus);
                    } else if (collectionType.equals(ObjectType.EVENT)){
                        Optional<CollectionEventEntity> collectionEventEntity = collectionEventDAO.findByCollectionIdAndEventId(collectionEntity.getId(), objectId);

                        if(collectionEventEntity.isPresent()){
                            collectionEventDAO.delete(collectionEventEntity.get());
                        } else {
                            throw new IllegalStateException("Object isn't present in the collection");
                        }

                        CollectionStatus collectionStatus = new CollectionStatus();
                        collectionStatus.setItemStatus(false);
                        collectionStatus.setCollectionName(collectionName);

                        return ResponseEntity.ok(collectionStatus);
                    } else {
                        throw new IllegalArgumentException("Collection type must be SPOT or EVENT");
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(400).body(null);
        }
    }
}
