package com.spotlightsarajevo.modules.event.service;

import com.spotlightsarajevo.modules.event.domain.EventDAO;
import com.spotlightsarajevo.modules.event.api.dto.EventCreateModel;
import com.spotlightsarajevo.modules.event.api.dto.EventModel;
import com.spotlightsarajevo.modules.event.api.dto.EventShorthandModel;
import com.spotlightsarajevo.modules.event.api.dto.EventUpdateModel;
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
}
