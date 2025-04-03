package ba.spotlightsarajevo.service.implementation.service;

import ba.spotlightsarajevo.dao.SpotDAO;
import ba.spotlightsarajevo.dao.entities.SpotEntity;
import ba.spotlightsarajevo.dao.models.spot.SpotCreate;
import ba.spotlightsarajevo.dao.models.spot.SpotModel;
import ba.spotlightsarajevo.service.definition.mapper.SpotMapper;
import ba.spotlightsarajevo.service.definition.service.SpotService;
import ba.spotlightsarajevo.service.definition.validator.SpotValidator;
import ba.spotlightsarajevo.utils.EntityRequest;
import ba.spotlightsarajevo.utils.EntityResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class SpotServiceImpl implements SpotService {
    SpotDAO spotDAO;
    SpotMapper spotMapper;
    SpotValidator spotValidator;

    @Override
    public EntityResponse<SpotModel> create(EntityRequest<SpotCreate> request) {
        SpotCreate spotCreate = request.getData();
        spotValidator.validateCreateRequest(spotCreate);

        SpotEntity newEntity = spotMapper.dtoToEntity(spotCreate);
        spotDAO.persist(newEntity);

        return new EntityResponse<SpotModel>("Spot persisted", "200 OK", LocalDateTime.now(), spotMapper.entityToDto(newEntity));
    }

    @Override
    public EntityResponse<SpotModel> findById(EntityRequest<Integer> request) {
        return null;
    }
}
