package ba.spotlightsarajevo.service.implementation.service;

import ba.spotlightsarajevo.dao.CategoryDAO;
import ba.spotlightsarajevo.dao.SpotDAO;
import ba.spotlightsarajevo.dao.entities.CategoryEntity;
import ba.spotlightsarajevo.dao.entities.SpotEntity;
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

import java.util.List;
import java.util.Optional;
import java.util.Random;

@AllArgsConstructor
@Service
public class SpotServiceImpl implements SpotService {
    SpotDAO spotDAO;
    CategoryDAO categoryDAO;
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

        Page<SpotModel> spotResponse = new PageImpl<>(
                spotModelList,
                pageRequest,
                pagedSpotResponse.getTotalElements()
        );

        return ResponseEntity.ok(spotResponse);
    }

    @Override
    public ResponseEntity<SpotShorthand> getSpotHeadline() {
        Random rand = new Random();
        List<SpotEntity> entities = spotDAO.findAll();
        int totalItems = entities.size();

        SpotEntity entity = entities.get(rand.nextInt(totalItems));

        SpotShorthand response = spotMapper.entityToShorthandDto(entity);
        Optional<CategoryEntity> spotCategory = categoryDAO.findById(entity.getCategoryId());
        if(spotCategory.isPresent()){
            response.setCategoryName(spotCategory.get().getCategoryName());
        }

        lookupImagesService.lookupThumbnailImage(response, ObjectType.SPOT, response.getId());

        return ResponseEntity.status(200).body(response);
    }

    @Override
    public ResponseEntity<SpotModel> findBySlug(SSEntityRequest<String> request) {
        return null;
    }
}
