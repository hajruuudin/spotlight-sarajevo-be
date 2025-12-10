package com.spotlightsarajevo.modules.event.service;

import com.spotlightsarajevo.modules.event.api.dto.*;
import com.spotlightsarajevo.modules.event.domain.EventDAO;
import com.spotlightsarajevo.modules.event.domain.entity.EventEntity;
import com.spotlightsarajevo.modules.event.utils.EventUtilities;
import com.spotlightsarajevo.modules.media.service.LookupImagesService;
import com.spotlightsarajevo.modules.event.mapper.EventMapper;
import com.spotlightsarajevo.common.enums.ExceptionCodes;
import com.spotlightsarajevo.common.enums.ObjectType;
import com.spotlightsarajevo.common.exceptions.EventExceptions;
import com.spotlightsarajevo.common.specifications.EventSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    EventDAO eventDAO;
    EventUtilities utils;
    EventMapper eventMapper;
    LookupImagesService lookupImagesService;

    @Override
    public ResponseEntity<Page<EventShorthandModel>> findEventsPaginated(PageRequest pageRequest, String searchTerm, String sortOption, List<Integer> categoryIds) {
        if(pageRequest == null){
            throw new EventExceptions.EventSystemException(ExceptionCodes.EventExceptionCodes.INVALID_REQUEST_CONTENT);
        }

        if(categoryIds == null){
            categoryIds = new ArrayList<>();
        }

        PageRequest endPageRequest = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize());

        Specification<EventEntity> spec = Specification
                .allOf(EventSpecification.hasSearchTerms(searchTerm))
                .and(EventSpecification.hasCategories(categoryIds))
                .and(EventSpecification.withDynamicSorting(sortOption));

        Page<EventEntity> eventEntities = eventDAO.findAll(spec, endPageRequest);

        for(EventEntity eventEntity : eventEntities.getContent()){
            utils.setEventCategories(eventEntity);
            utils.setEventTags(eventEntity);
            lookupImagesService.lookupThumbnailImage(eventEntity, ObjectType.EVENT, eventEntity.getId());
        }

        Page<EventShorthandModel> responseList = new PageImpl<>(
                eventMapper.entitiesToShorthandDtos(eventEntities.getContent()),
                endPageRequest,
                eventEntities.getTotalElements()
        );
        return ResponseEntity.status(200).body(responseList);
    }

    @Override
    public ResponseEntity<EventModel> create(EventCreateModel eventCreateModel) {
        return null;
    }

    @Override
    public ResponseEntity<EventModel> update(EventUpdateModel eventUpdateModel) {
        return null;
    }

    @Override
    public ResponseEntity<EventModel> delete(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<EventOverviewModel> findEventOverview(String eventSlug) {
        if(eventSlug == null || eventSlug.isBlank()){
            throw new EventExceptions.EventNotFoundException(ExceptionCodes.EventExceptionCodes.EVENT_NOT_FOUND.toString());
        }

        Optional<EventEntity> eventEntity = eventDAO.findBySlug(eventSlug);

        if(eventEntity.isEmpty()){
            throw new EventExceptions.EventNotFoundException(ExceptionCodes.EventExceptionCodes.EVENT_NOT_FOUND.toString());
        } else {
            utils.setEventCategories(eventEntity.get());
            utils.setEventTags(eventEntity.get());
            utils.setEventOrganiser(eventEntity.get());

            EventOverviewModel eventOverviewModel = eventMapper.entityToOverviewDto(eventEntity.get());

            lookupImagesService.lookupThumbnailImage(eventOverviewModel, ObjectType.EVENT, eventEntity.get().getId());
            lookupImagesService.lookupAllImages(eventOverviewModel, ObjectType.EVENT, eventEntity.get().getId());

            return ResponseEntity.status(200).body(eventOverviewModel);
        }
    }
}
