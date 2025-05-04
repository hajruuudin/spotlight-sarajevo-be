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
import com.google.api.client.util.DateTime;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter reverseDateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d");

    @Override
    public ResponseEntity<EventModel> create(SSEntityRequest<EventCreate> request) {
        return null;
    }

    @Override
    public ResponseEntity<Page<EventShorthand>> getEventsShorthand(PageRequest request, String search, String sort, List<Integer> categoryIds) {
        if (categoryIds != null && categoryIds.isEmpty()) {
            categoryIds = null;
        }

        Page<EventEntity> pagedEventShorthandResponse = eventDAO.findAll(request, search, categoryIds, sort);
        List<EventEntity> eventEntities = pagedEventShorthandResponse.getContent();


        for(EventEntity entity : eventEntities){

            /* SETTING THE EVENT CATEGORIES */
            Optional<CategoryEntity> categoryEntity = categoryDAO.findById(entity.getCategoryId());
            categoryEntity.ifPresent(category -> entity.setCategoryName(category.getCategoryName()));

            /* SETTING THE EVENT TAGS */
            List<EventTagEntity> eventTags = eventTagDAO.findAllTagsById(entity.getId());
            List<String> tagNames = new ArrayList<>();

            for(EventTagEntity eventTag : eventTags){
                Optional<TagEntity> tag = tagDAO.findById(eventTag.getTagId());
                tag.ifPresent(tagEntity -> tagNames.add(tagEntity.getTagName()));
            }

            /* FORMATTING THE EVENT DATE */
            LocalDateTime eventStartDate = entity.getStartDate();
            String formattedEventStartDate = eventStartDate.format(reverseDateFormatter);
            entity.setStartDateFormatted(formattedEventStartDate);

            entity.setTagNames(tagNames);
        }

        List<EventShorthand> eventShorthandsList = eventMapper.entitiesToShorthandDtos(pagedEventShorthandResponse.getContent());


        for(EventShorthand event : eventShorthandsList){
            lookupImagesService.lookupThumbnailImage(event, ObjectType.EVENT, event.getId());
        }

        Page<EventShorthand> eventResponse = new PageImpl<>(
                eventShorthandsList,
                request,
                pagedEventShorthandResponse.getTotalElements()
        );

        return ResponseEntity.ok(eventResponse);
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
    public ResponseEntity<Page<EventShorthand>> getEventsByDate(PageRequest request, String selectedQueryDate) {
        LocalDate date = LocalDate.parse(selectedQueryDate, dateFormatter);
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        Page<EventEntity> eventShorthandResponse = eventDAO.findByStartDate(request, startOfDay, endOfDay);

        List<EventEntity> eventEntities = eventShorthandResponse.getContent();

        for(EventEntity entity : eventEntities){

            /* SETTING THE EVENT CATEGORIES */
            Optional<CategoryEntity> categoryEntity = categoryDAO.findById(entity.getCategoryId());
            categoryEntity.ifPresent(category -> entity.setCategoryName(category.getCategoryName()));

            /* SETTING THE EVENT TAGS */
            List<EventTagEntity> eventTags = eventTagDAO.findAllTagsById(entity.getId());
            List<String> tagNames = new ArrayList<>();

            for(EventTagEntity eventTag : eventTags){
                Optional<TagEntity> tag = tagDAO.findById(eventTag.getTagId());
                tag.ifPresent(tagEntity -> tagNames.add(tagEntity.getTagName()));
            }

            entity.setTagNames(tagNames);
        }

        List<EventShorthand> eventShorthandList = eventMapper.entitiesToShorthandDtos(eventShorthandResponse.getContent());

        for(EventShorthand eventShorthand : eventShorthandList){
            lookupImagesService.lookupThumbnailImage(eventShorthand, ObjectType.EVENT, eventShorthand.getId());
        }

        Page<EventShorthand> response = new PageImpl<>(
                eventShorthandList,
                request,
                eventShorthandResponse.getTotalElements()
        );

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<EventModel> findBySlug(SSEntityRequest<String> request) {
        return null;
    }

    @Override
    public ResponseEntity<Page<EventModel>> getEventsPaginated(PageRequest request) {
        return null;
    }
}
