package ba.spotlightsarajevo.service.implementation.service;

import ba.spotlightsarajevo.dao.CategoryDAO;
import ba.spotlightsarajevo.dao.EventDAO;
import ba.spotlightsarajevo.dao.EventTagDAO;
import ba.spotlightsarajevo.dao.TagDAO;
import ba.spotlightsarajevo.dao.entities.*;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    EventDAO eventDAO;
    EventMapper eventMapper;
    CategoryDAO categoryDAO;
    EventTagDAO eventTagDAO;
    TagDAO tagDAO;
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

        Optional<CategoryEntity> eventCategory = categoryDAO.findById(entity.getCategoryId());
        eventCategory.ifPresent(categoryEntity -> entity.setCategoryName(categoryEntity.getCategoryName()));

        List<EventTagEntity> eventTags = eventTagDAO.findAllTagsById(entity.getId());
        List<String> tagNames = new ArrayList<>();

        for(EventTagEntity eventTag : eventTags){
            Optional<TagEntity> tag = tagDAO.findById(eventTag.getTagId());
            tag.ifPresent(tagEntity -> tagNames.add(tagEntity.getTagName()));
        }

        entity.setTagNames(tagNames);

        EventShorthand response = eventMapper.entityToShorthandDto(entity);
        lookupImagesService.lookupThumbnailImage(response, ObjectType.EVENT, response.getId());

        return ResponseEntity.status(200).body(response);
    }

    @Override
    public ResponseEntity<EventModel> findBySlug(SSEntityRequest<String> request) {
        return null;
    }
}
