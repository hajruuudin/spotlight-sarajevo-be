package com.spotlightsarajevo.service.implementation;

import com.spotlightsarajevo.dao.access.SpotDAO;
import com.spotlightsarajevo.dao.dto.spot.SpotShorthandModel;
import com.spotlightsarajevo.dao.entity.SpotEntity;
import com.spotlightsarajevo.dao.dto.spot.SpotCreateModel;
import com.spotlightsarajevo.dao.dto.spot.SpotModel;
import com.spotlightsarajevo.dao.dto.spot.SpotUpdateModel;
import com.spotlightsarajevo.service.definition.SpotService;
import com.spotlightsarajevo.service.mappers.SpotMapper;
import com.spotlightsarajevo.utils.enums.ExceptionCodes;
import com.spotlightsarajevo.utils.enums.SortingOptions;
import com.spotlightsarajevo.utils.exceptions.SpotExceptions;
import com.spotlightsarajevo.utils.helpers.CommonFunctions;
import com.spotlightsarajevo.utils.specifications.SpotSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.rmi.NoSuchObjectException;
import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@Service
public class SpotServiceImpl implements SpotService {
    SpotDAO spotDAO;
    SpotMapper spotMapper;
    CommonFunctions utils;

    @Override
    public ResponseEntity<Page<SpotModel>> findAll(PageRequest pageRequest) {
        try{
            Page<SpotEntity> pagedEntities = spotDAO.findAll(pageRequest);

            List<SpotModel> spotModelList = spotMapper.entitiesToDtos(pagedEntities.getContent());

            Page<SpotModel> responseList = new PageImpl<>(
                    spotModelList,
                    pageRequest,
                    pagedEntities.getTotalElements()
            );

            return ResponseEntity.status(200).body(responseList);
        } catch (Exception e) {
            throw new SpotExceptions.SpotNotFoundException("ERROR");
        }
    }

    @Override
    public ResponseEntity<Page<SpotShorthandModel>> findSpotsPaginated(PageRequest pageRequest, String searchTerm, String sortOption, List<Integer> categoryIds) {
        if(pageRequest == null){
            throw new SpotExceptions.SpotSystemException(ExceptionCodes.SpotExceptionCodes.INVALID_REQUEST_CONTENT);
        }

        PageRequest endPageRequest = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize());

        Specification<SpotEntity> spec = Specification
                .allOf(SpotSpecifications.hasSearchTerm(searchTerm))
                .and(SpotSpecifications.hasCategories(categoryIds))
                .and(SpotSpecifications.withDynamicSorting(sortOption));

        Page<SpotEntity> spotEntities = spotDAO.findAll(spec, endPageRequest);

        for(SpotEntity spotEntity : spotEntities.getContent()){
            utils.setSpotCategoriesAndRating(spotEntity);
        }

        Page<SpotShorthandModel> responseList = new PageImpl<>(
                spotMapper.entitiesToShorthandDtos(spotEntities.getContent()),
                endPageRequest,
                spotEntities.getTotalElements()
        );
        return ResponseEntity.status(200).body(responseList);
    }

    @Override
    public ResponseEntity<SpotModel> create(SpotCreateModel spotCreateModel) {
        try{
            SpotEntity newSpotEntity = spotMapper.dtoToEntity(spotCreateModel);
            newSpotEntity.setCreatedBy("Admin User");
            newSpotEntity.setCreated(LocalDateTime.now());

            SpotEntity addedEntity = spotDAO.save(newSpotEntity);

            return ResponseEntity.status(200).body(spotMapper.entityToDto(addedEntity));
        } catch (Exception e){
            throw new SpotExceptions.SpotCreationException("Could not create spot");
        }
    }

    @Override
    public ResponseEntity<SpotModel> update(SpotUpdateModel spotUpdateModel){
        try{
            Optional<SpotEntity> existingSpot = spotDAO.findById(spotUpdateModel.getId());

            if(existingSpot.isEmpty()){
                throw new NoSuchObjectException("No Spot with given ID Found");
            } else {
                spotMapper.updateEntityFromDto(spotUpdateModel, existingSpot.get());
                existingSpot.get().setModified(LocalDateTime.now());
                existingSpot.get().setModifiedBy("Admin User");

                spotDAO.save(existingSpot.get());

                return ResponseEntity.status(200).body(spotMapper.entityToDto(existingSpot.get()));
            }
        } catch (NoSuchObjectException e) {
            throw new SpotExceptions.SpotNotFoundException(spotUpdateModel.getId().toString());
        } catch (Exception e){
            throw new SpotExceptions.SpotConflictException("Could not update spot");
        }
    }

    @Override
    public ResponseEntity<SpotModel> delete(Integer id){
        try{
            Optional<SpotEntity> existingSpot = spotDAO.findById(id);

            if(existingSpot.isPresent()){
                spotDAO.delete(existingSpot.get());

                return ResponseEntity.status(200).body(spotMapper.entityToDto(existingSpot.get()));
            } else {
                throw new RuntimeException();
            }
        } catch (Exception e){
            throw new SpotExceptions.SpotUnauthorizedException("Could not delete spot");
        }
    }
}
