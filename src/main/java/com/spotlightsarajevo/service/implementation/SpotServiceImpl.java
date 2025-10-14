package com.spotlightsarajevo.service.implementation;

import com.spotlightsarajevo.dao.access.SpotDAO;
import com.spotlightsarajevo.dao.entities.SpotEntity;
import com.spotlightsarajevo.dao.models.spot.SpotCreateModel;
import com.spotlightsarajevo.dao.models.spot.SpotModel;
import com.spotlightsarajevo.dao.models.spot.SpotUpdateModel;
import com.spotlightsarajevo.service.definition.SpotService;
import com.spotlightsarajevo.service.mappers.SpotMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.rmi.NoSuchObjectException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SpotServiceImpl implements SpotService {
    SpotDAO spotDAO;
    SpotMapper spotMapper;

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
            throw new RuntimeException("ERROR: Spot Find All Error");
        }
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
            throw new RuntimeException("ERROR - Spot Add Error:" + e);
        }
    }

    @Override
    public ResponseEntity<SpotModel> update(SpotUpdateModel spotUpdateModel){
        try{
            Optional<SpotEntity> existingSpot = spotDAO.findById(spotUpdateModel.getId());

            if(!existingSpot.isPresent()){
                throw new NoSuchObjectException("No Spot with given ID Found");
            } else {
                spotMapper.updateEntityFromDto(spotUpdateModel, existingSpot.get());
                existingSpot.get().setModified(LocalDateTime.now());
                existingSpot.get().setModifiedBy("Admin User");

                spotDAO.save(existingSpot.get());

                return ResponseEntity.status(200).body(spotMapper.entityToDto(existingSpot.get()));
            }
        } catch (NoSuchObjectException e) {
            throw new RuntimeException("No such spot found");
        } catch (Exception e){
            throw new RuntimeException("ERROR - Spot Update Error" + e);
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
            throw new RuntimeException("ERROR - Spot Delete Error" + e);
        }
    }
}
