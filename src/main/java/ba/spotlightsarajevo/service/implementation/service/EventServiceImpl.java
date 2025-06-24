package ba.spotlightsarajevo.service.implementation.service;

import ba.spotlightsarajevo.dao.*;
import ba.spotlightsarajevo.dao.entities.*;
import ba.spotlightsarajevo.dao.models.event.EventCreate;
import ba.spotlightsarajevo.dao.models.event.EventModel;
import ba.spotlightsarajevo.dao.models.event.EventShorthand;
import ba.spotlightsarajevo.dao.models.event.EventUpdate;
import ba.spotlightsarajevo.enums.ObjectType;
import ba.spotlightsarajevo.service.definition.mapper.EventMapper;
import ba.spotlightsarajevo.service.definition.service.EventService;
import ba.spotlightsarajevo.utils.ObjectUtils;
import ba.spotlightsarajevo.utils.SSEntityRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.rmi.NoSuchObjectException;
import java.security.Principal;
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
    UserDAO userDAO;
    EventMapper eventMapper;
    CategoryDAO categoryDAO;
    EventTagDAO eventTagDAO;
    TagDAO tagDAO;
    LookupImagesService lookupImagesService;
    ObjectUtils objectUtils;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter reverseDateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d");

    @Override
    @Transactional
    public ResponseEntity<EventModel> create(EventCreate request, Principal principal) {
        try {
            UserEntity adminUser = userDAO.findByEmail(principal.getName());

            if(!adminUser.getIsAdmin()){
                throw new IllegalAccessException();
            } else {
                EventEntity newEvent = new EventEntity();
                objectUtils.addEventBase(request, newEvent, adminUser, categoryDAO);

                EventEntity addedEvent = eventDAO.save(newEvent);

                objectUtils.addEventTags(request, addedEvent, tagDAO, eventTagDAO);

                return ResponseEntity.ok(eventMapper.entityToDto(addedEvent));
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Event Create ERROR");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("User cannot access this function");
        }
    }

    @Override
    public ResponseEntity<Page<EventShorthand>> getEventsShorthand(PageRequest request, String search, String sort, List<Integer> categoryIds) {
        if (categoryIds != null && categoryIds.isEmpty()) {
            categoryIds = null;
        }

        Page<EventEntity> pagedEventShorthandResponse = eventDAO.findAll(request, search, categoryIds, sort);
        List<EventEntity> eventEntities = pagedEventShorthandResponse.getContent();


        for(EventEntity entity : eventEntities){
            objectUtils.setEventInformation(categoryDAO, eventTagDAO, tagDAO, entity);
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
            objectUtils.setEventInformation(categoryDAO, eventTagDAO, tagDAO, entity);
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
    public ResponseEntity<EventModel> getEventOverview(SSEntityRequest<String> request) {
        try {
            EventEntity entity = eventDAO.findBySlug(request.getData());

            objectUtils.setEventInformation(categoryDAO, eventTagDAO, tagDAO, entity);

            EventModel eventModel = eventMapper.entityToDto(entity);

            lookupImagesService.lookupThumbnailImage(eventModel, ObjectType.EVENT, eventModel.getId());

            return ResponseEntity.ok(eventModel);
        } catch (Exception e){
            return ResponseEntity.status(400).body(null);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<EventModel> updateEvent(EventUpdate request, Principal principal) {
        try {
            UserEntity adminUser = userDAO.findByEmail(principal.getName());

            if(!adminUser.getIsAdmin()){
                throw new IllegalAccessException("");
            } else {
                Optional<EventEntity> existingEvent = eventDAO.findById(request.getId());

                if(!existingEvent.isPresent()){
                    throw new NoSuchObjectException("");
                } else {
                    objectUtils.formatEventUpdate(request, existingEvent.get(), adminUser, categoryDAO, tagDAO, eventTagDAO);

                    EventEntity updatedEvent = eventDAO.save(existingEvent.get());

                    return ResponseEntity.ok(eventMapper.entityToDto(updatedEvent));
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Event Update ERROR");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("User cannot access this function");
        } catch (NoSuchObjectException e) {
            throw new RuntimeException("Event with given ID does not exist");
        }
    }

}
