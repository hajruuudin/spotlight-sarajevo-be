package com.spotlightsarajevo.service.implementation;

import com.spotlightsarajevo.dao.access.EventDAO;
import com.spotlightsarajevo.dao.dto.event.EventCreateModel;
import com.spotlightsarajevo.dao.dto.event.EventModel;
import com.spotlightsarajevo.dao.dto.event.EventShorthandModel;
import com.spotlightsarajevo.dao.dto.event.EventUpdateModel;
import com.spotlightsarajevo.dao.dto.spot.SpotShorthandModel;
import com.spotlightsarajevo.dao.entity.EventEntity;
import com.spotlightsarajevo.dao.entity.SpotEntity;
import com.spotlightsarajevo.service.definition.EventService;
import com.spotlightsarajevo.service.definition.LookupImagesService;
import com.spotlightsarajevo.service.mappers.EventMapper;
import com.spotlightsarajevo.utils.enums.ExceptionCodes;
import com.spotlightsarajevo.utils.enums.ObjectType;
import com.spotlightsarajevo.utils.exceptions.EventExceptions;
import com.spotlightsarajevo.utils.exceptions.SpotExceptions;
import com.spotlightsarajevo.utils.helpers.CommonFunctions;
import com.spotlightsarajevo.utils.specifications.EventSpecification;
import com.spotlightsarajevo.utils.specifications.SpotSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    EventDAO eventDAO;
    CommonFunctions utils;
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
