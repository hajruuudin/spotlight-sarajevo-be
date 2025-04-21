package ba.spotlightsarajevo.service.implementation.service;

import ba.spotlightsarajevo.dao.CategoryDAO;
import ba.spotlightsarajevo.dao.EventDAO;
import ba.spotlightsarajevo.dao.entities.CategoryEntity;
import ba.spotlightsarajevo.dao.entities.EventEntity;
import ba.spotlightsarajevo.dao.models.event.EventCreate;
import ba.spotlightsarajevo.dao.models.event.EventModel;
import ba.spotlightsarajevo.dao.models.event.EventShorthand;
import ba.spotlightsarajevo.enums.ObjectType;
import ba.spotlightsarajevo.service.definition.mapper.EventMapper;
import ba.spotlightsarajevo.service.definition.service.EventService;
import ba.spotlightsarajevo.utils.SSEntityRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    EventDAO eventDAO;
    EventMapper eventMapper;
    CategoryDAO categoryDAO;
    LookupImagesService lookupImagesService;

    @Override
    public ResponseEntity<EventModel> create(SSEntityRequest<EventCreate> request) {
        return null;
    }

    @Override
    public ResponseEntity<Page<EventModel>> getEventsPaginated(PageRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<EventShorthand> getEventHeadline() {
        Random rand = new Random();
        List<EventEntity> entities = eventDAO.findAll();
        int totalItems = entities.size();

        EventEntity entity = entities.get(rand.nextInt(totalItems));

        EventShorthand response = eventMapper.entityToShorthandDto(entity);
        Optional<CategoryEntity> eventCategory = categoryDAO.findById(entity.getCategoryId());
        if(eventCategory.isPresent()){
            response.setCategoryName(eventCategory.get().getCategoryName());
        }

        lookupImagesService.lookupThumbnailImage(response, ObjectType.EVENT, response.getId());

        return ResponseEntity.status(200).body(response);
    }

    @Override
    public ResponseEntity<EventModel> findBySlug(SSEntityRequest<String> request) {
        return null;
    }
}
