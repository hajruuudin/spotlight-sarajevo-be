package com.spotlightsarajevo.modules.collection.service;

import com.spotlightsarajevo.common.enums.ExceptionCodes;
import com.spotlightsarajevo.common.enums.ObjectType;
import com.spotlightsarajevo.common.exceptions.CollectionExceptions;
import com.spotlightsarajevo.common.utils.CollectionItem;
import com.spotlightsarajevo.common.utils.CommonFunctions;
import com.spotlightsarajevo.modules.collection.api.dto.*;
import com.spotlightsarajevo.modules.collection.domain.CollectionDAO;
import com.spotlightsarajevo.modules.collection.domain.CollectionEventDAO;
import com.spotlightsarajevo.modules.collection.domain.CollectionSpotDAO;
import com.spotlightsarajevo.modules.collection.domain.entity.CollectionEntity;
import com.spotlightsarajevo.modules.collection.domain.entity.CollectionEventEntity;
import com.spotlightsarajevo.modules.collection.domain.entity.CollectionSpotEntity;
import com.spotlightsarajevo.modules.collection.mapper.CollectionMapper;
import com.spotlightsarajevo.modules.collection.utils.CollectionUtils;
import com.spotlightsarajevo.modules.user.domain.UserDAO;
import com.spotlightsarajevo.modules.user.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CollectionServiceImpl implements CollectionService {
    CollectionDAO collectionDAO;
    CollectionSpotDAO collectionSpotDAO;
    CollectionEventDAO collectionEventDAO;
    CollectionMapper collectionMapper;
    CollectionUtils collectionUtils;
    CommonFunctions commonFunctions;

    @Override
    public ResponseEntity<List<CollectionModel>> findUserCollections(Principal principal) {
        if(principal == null) throw new CollectionExceptions.CollectionUnauthorisedException(ExceptionCodes.CollectionExceptionCodes.COLLECTION_FIND_UNAUTHORISED);

        UserEntity user = commonFunctions.getUserFromPrincipal(principal);

        List<CollectionEntity> entities = collectionDAO.findByUserId(user.getId());
        List<CollectionModel> models = collectionMapper.entitiesToDtos(entities);

        return ResponseEntity.status(200).body(models);
    }

    @Override
    public ResponseEntity<CollectionModel> addCollection(CollectionCreateModel request, Principal principal) {
        if(principal == null) throw new CollectionExceptions.CollectionUnauthorisedException(ExceptionCodes.CollectionExceptionCodes.COLLECTION_FIND_UNAUTHORISED);
        if(request == null) throw new CollectionExceptions.CollectionRequestContentException(ExceptionCodes.CollectionExceptionCodes.COLLECTION_REQUEST_INVALID);
        if(!request.getCollectionType().equals(ObjectType.SPOT.toString()) || !request.getCollectionType().equals(ObjectType.EVENT.toString()))
            throw new CollectionExceptions.CollectionRequestContentException(ExceptionCodes.CollectionExceptionCodes.COLLECTION_REQUEST_INVALID);

        UserEntity user = commonFunctions.getUserFromPrincipal(principal);

        CollectionEntity entity = collectionMapper.dtoToEntity(request);
        entity.setCreated(LocalDateTime.now());
        entity.setCreatedBy(user.getUsername());
        entity.setUserId(user.getId());

        CollectionEntity savedEntity = collectionDAO.save(entity);

        return ResponseEntity.status(200).body(collectionMapper.entityToDto(savedEntity));
    }

    @Override
    public ResponseEntity<CollectionModel> updateCollection(CollectionUpdateModel request, Principal principal) {
        if(principal == null) throw new CollectionExceptions.CollectionUnauthorisedException(ExceptionCodes.CollectionExceptionCodes.COLLECTION_FIND_UNAUTHORISED);
        if(request == null) throw new CollectionExceptions.CollectionRequestContentException(ExceptionCodes.CollectionExceptionCodes.COLLECTION_REQUEST_INVALID);

        UserEntity user = commonFunctions.getUserFromPrincipal(principal);

        Optional<CollectionEntity> entity = collectionDAO.findById(request.getId());
        if (entity.isPresent()){
            collectionMapper.updateEntityFromDto(request, entity.get());
            collectionDAO.save(entity.get());

            return ResponseEntity.status(200).body(collectionMapper.entityToDto(entity.get()));
        } else {
            throw new CollectionExceptions.CollectionNotFoundException(ExceptionCodes.CollectionExceptionCodes.COLLECTION_NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<CollectionModel> deleteCollection(Integer collectionId, Principal principal) {
        if(principal == null) throw new CollectionExceptions.CollectionUnauthorisedException(ExceptionCodes.CollectionExceptionCodes.COLLECTION_FIND_UNAUTHORISED);
        if(collectionId == null) throw new CollectionExceptions.CollectionRequestContentException(ExceptionCodes.CollectionExceptionCodes.COLLECTION_REQUEST_INVALID);

        UserEntity user = commonFunctions.getUserFromPrincipal(principal);
        collectionUtils.checkAuthentication(collectionId, user.getId());

        Optional<CollectionEntity> entity = collectionDAO.findById(collectionId);
        if(entity.isPresent()){
            collectionDAO.delete(entity.get());

            return ResponseEntity.status(200).body(collectionMapper.entityToDto(entity.get()));
        } else {
            throw new CollectionExceptions.CollectionNotFoundException(ExceptionCodes.CollectionExceptionCodes.COLLECTION_NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<CollectionItemsModel> findCollectionItems(Integer collectionId, Principal principal) {
        if(principal == null) throw new CollectionExceptions.CollectionUnauthorisedException(ExceptionCodes.CollectionExceptionCodes.COLLECTION_FIND_UNAUTHORISED);
        if(collectionId == null) throw new CollectionExceptions.CollectionRequestContentException(ExceptionCodes.CollectionExceptionCodes.COLLECTION_REQUEST_INVALID);

        UserEntity user = commonFunctions.getUserFromPrincipal(principal);

        Optional<CollectionEntity> collectionEntity = collectionDAO.findById(collectionId);

        if(collectionEntity.isPresent()){
            if(Objects.equals(collectionEntity.get().getCollectionType(), ObjectType.SPOT.toString())){
                List<CollectionItem> collectionSpotEntities = collectionSpotDAO.findAllByCollectionId(collectionId);

                CollectionItemsModel collectionItemsModel = new CollectionItemsModel();
                collectionItemsModel.setCollectionId(collectionId);
                collectionItemsModel.setCollectionType(collectionEntity.get().getCollectionType());
                collectionItemsModel.setCollectionItems(collectionSpotEntities);

                return ResponseEntity.status(200).body(collectionItemsModel);
            } else if (Objects.equals(collectionEntity.get().getCollectionType(), ObjectType.EVENT.toString())){
                List<CollectionItem> collectionEventEntities = collectionEventDAO.findAllByCollectionId(collectionId);

                CollectionItemsModel collectionItemsModel = new CollectionItemsModel();
                collectionItemsModel.setCollectionId(collectionId);
                collectionItemsModel.setCollectionType(collectionEntity.get().getCollectionType());
                collectionItemsModel.setCollectionItems(collectionEventEntities);

                return ResponseEntity.status(200).body(collectionItemsModel);
            } else {
                throw new CollectionExceptions.CollectionRequestContentException(ExceptionCodes.CollectionExceptionCodes.COLLECTION_INVALID_TYPE);
            }
        } else {
            throw new CollectionExceptions.CollectionNotFoundException(ExceptionCodes.CollectionExceptionCodes.COLLECTION_NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<CollectionItemsModel> addItemToCollection(CollectionAddItemModel request, Principal principal) {
        if(principal == null) throw new CollectionExceptions.CollectionUnauthorisedException(ExceptionCodes.CollectionExceptionCodes.COLLECTION_FIND_UNAUTHORISED);
        if(request == null) throw new CollectionExceptions.CollectionRequestContentException(ExceptionCodes.CollectionExceptionCodes.COLLECTION_REQUEST_INVALID);

        UserEntity user = commonFunctions.getUserFromPrincipal(principal);
        collectionUtils.checkAuthentication(request.getCollectionId(), user.getId());

        Optional<CollectionEntity> collectionEntityOptional = collectionDAO.findById(request.getCollectionId());

        if (collectionEntityOptional.isEmpty()) {
            throw new CollectionExceptions.CollectionNotFoundException(ExceptionCodes.CollectionExceptionCodes.COLLECTION_NOT_FOUND);
        }

        CollectionEntity collectionEntity = collectionEntityOptional.get();
        String collectionType = collectionEntity.getCollectionType();

        if (Objects.equals(collectionType, ObjectType.SPOT.toString())){
            CollectionSpotEntity spotEntity = collectionMapper.itemSpotDtoToEntity(request);
            collectionSpotDAO.save(spotEntity);

            List<CollectionItem> collectionSpotEntities = collectionSpotDAO.findAllByCollectionId(request.getCollectionId());
            CollectionItemsModel collectionItemsModel = new CollectionItemsModel();
            collectionItemsModel.setCollectionId(request.getCollectionId());
            collectionItemsModel.setCollectionType(collectionType);
            collectionItemsModel.setCollectionItems(collectionSpotEntities);

            return ResponseEntity.status(200).body(collectionItemsModel);
        } else if (Objects.equals(collectionType, ObjectType.EVENT.toString())){
            CollectionEventEntity eventEntity = collectionMapper.itemEvenDtoToEntity(request);
            collectionEventDAO.save(eventEntity);

            List<CollectionItem> collectionEventEntities = collectionEventDAO.findAllByCollectionId(request.getCollectionId());
            CollectionItemsModel collectionItemsModel = new CollectionItemsModel();
            collectionItemsModel.setCollectionId(request.getCollectionId());
            collectionItemsModel.setCollectionType(collectionType);
            collectionItemsModel.setCollectionItems(collectionEventEntities);

            return ResponseEntity.status(200).body(collectionItemsModel);
        } else {
            throw new CollectionExceptions.CollectionRequestContentException(ExceptionCodes.CollectionExceptionCodes.COLLECTION_INVALID_TYPE);
        }
    }

    @Override
    public ResponseEntity<CollectionItemsModel> removeItemFromCollection(Integer collectionId, Integer itemId, String collectionType, Principal principal) {
        if(principal == null) throw new CollectionExceptions.CollectionUnauthorisedException(ExceptionCodes.CollectionExceptionCodes.COLLECTION_FIND_UNAUTHORISED);
        if(collectionId == null || itemId == null || collectionType == null)
            throw new CollectionExceptions.CollectionRequestContentException(ExceptionCodes.CollectionExceptionCodes.COLLECTION_REQUEST_INVALID);
        if (!collectionType.equals(ObjectType.SPOT.toString()) && !collectionType.equals(ObjectType.EVENT.toString()))
            throw new CollectionExceptions.CollectionRequestContentException(ExceptionCodes.CollectionExceptionCodes.COLLECTION_INVALID_TYPE);

        UserEntity user = commonFunctions.getUserFromPrincipal(principal);
        collectionUtils.checkAuthentication(collectionId, user.getId());

        if (Objects.equals(collectionType, ObjectType.SPOT.toString())){
            Optional<CollectionSpotEntity> itemToRemove = collectionSpotDAO.findByCollectionIdAndSpotId(collectionId, itemId);

            if (itemToRemove.isPresent()) {
                collectionSpotDAO.delete(itemToRemove.get());
            } else {
                throw new CollectionExceptions.CollectionItemNotFoundException(ExceptionCodes.CollectionExceptionCodes.COLLECTION_ITEM_NOT_FOUND);
            }

            List<CollectionItem> collectionSpotEntities = collectionSpotDAO.findAllByCollectionId(collectionId);
            CollectionItemsModel collectionItemsModel = new CollectionItemsModel();
            collectionItemsModel.setCollectionId(collectionId);
            collectionItemsModel.setCollectionType(collectionType);
            collectionItemsModel.setCollectionItems(collectionSpotEntities);

            return ResponseEntity.status(200).body(collectionItemsModel);

        } else if (Objects.equals(collectionType, ObjectType.EVENT.toString())){
            Optional<CollectionEventEntity> itemToRemove = collectionEventDAO.findByCollectionIdAndEventId(collectionId, itemId);

            if (itemToRemove.isPresent()) {
                collectionEventDAO.delete(itemToRemove.get());
            } else {
                throw new CollectionExceptions.CollectionItemNotFoundException(ExceptionCodes.CollectionExceptionCodes.COLLECTION_ITEM_NOT_FOUND);
            }

            List<CollectionItem> collectionEventEntities = collectionEventDAO.findAllByCollectionId(collectionId);
            CollectionItemsModel collectionItemsModel = new CollectionItemsModel();
            collectionItemsModel.setCollectionId(collectionId);
            collectionItemsModel.setCollectionType(collectionType);
            collectionItemsModel.setCollectionItems(collectionEventEntities);

            return ResponseEntity.status(200).body(collectionItemsModel);

        } else {
            throw new CollectionExceptions.CollectionRequestContentException(ExceptionCodes.CollectionExceptionCodes.COLLECTION_INVALID_TYPE);
        }
    }
}
