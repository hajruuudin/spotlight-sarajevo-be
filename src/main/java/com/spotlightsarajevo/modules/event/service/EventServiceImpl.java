package com.spotlightsarajevo.modules.event.service;

import com.spotlightsarajevo.common.exceptions.SpotExceptions;
import com.spotlightsarajevo.common.utils.CommonFunctions;
import com.spotlightsarajevo.modules.auth.domain.entity.UserEntity;
import com.spotlightsarajevo.modules.event.api.dto.*;
import com.spotlightsarajevo.modules.event.domain.EventDAO;
import com.spotlightsarajevo.modules.event.domain.entity.EventEntity;
import com.spotlightsarajevo.modules.event.utils.EventUtilities;
import com.spotlightsarajevo.modules.event.mapper.EventMapper;
import com.spotlightsarajevo.common.enums.ExceptionCodes;
import com.spotlightsarajevo.common.enums.ObjectType;
import com.spotlightsarajevo.common.exceptions.EventExceptions;
import com.spotlightsarajevo.common.specifications.EventSpecification;
import com.spotlightsarajevo.modules.media.api.dto.MediaStoreCreateModel;
import com.spotlightsarajevo.modules.media.domain.MediaStoreDAO;
import com.spotlightsarajevo.modules.media.domain.entity.MediaStoreEntity;
import com.spotlightsarajevo.modules.media.service.MediaService;
import com.spotlightsarajevo.modules.spot.api.dto.SpotWorkHoursModel;
import com.spotlightsarajevo.modules.spot.domain.entity.SpotEntity;
import com.spotlightsarajevo.modules.spot.domain.entity.SpotTagsEntity;
import com.spotlightsarajevo.modules.spot.domain.entity.SpotWorkHoursEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.w3c.dom.events.EventException;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    EventDAO eventDAO;
    EventUtilities utils;
    CommonFunctions commonFunctions;
    EventMapper eventMapper;
    MediaService mediaService;
    MediaStoreDAO mediaStoreDAO;

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
            mediaService.lookupThumbnailImage(eventEntity, ObjectType.EVENT, eventEntity.getId());
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
    public ResponseEntity<EventModel> update(EventUpdateModel eventUpdateModel, Principal principal) {
        if (principal == null) throw new EventExceptions.EventUnauthorizedException(ExceptionCodes.EventExceptionCodes.EVENT_UNAUTHORIZED_ACCESS);

        UserEntity user = commonFunctions.getUserFromPrincipal(principal);
        if (!user.getIsAdmin()) throw new EventExceptions.EventUnauthorizedException(ExceptionCodes.EventExceptionCodes.EVENT_UNAUTHORIZED_ACCESS);
        if (eventUpdateModel == null) throw new EventExceptions.EventSystemException(ExceptionCodes.EventExceptionCodes.INVALID_REQUEST_CONTENT);

        EventEntity entity = eventDAO.findById(eventUpdateModel.getId())
                .orElseThrow(() ->
                        new EventExceptions.EventNotFoundException(
                                ExceptionCodes.EventExceptionCodes.EVENT_NOT_FOUND));

        // From Section 1: Updating Basic Fields
        eventMapper.updateEntityFromDto(eventUpdateModel, entity);
        entity.setModified(LocalDateTime.now());
        entity.setModifiedBy(user.getUsername());

        // From Section 2: Update existing tags
        utils.updateEventTagsBulk(eventUpdateModel, entity);

        // From Section 3: Update Event Information (Handled by the first mapper section info section)

        // From Section 4: Handle updating thumbnail image and additional images
        if (eventUpdateModel.getNewThumbnailImage() != null) {
            MediaStoreEntity existingThumbnail = mediaStoreDAO.findThumbnailByItemIdAndItemCategory(entity.getId(), ObjectType.EVENT);
            mediaStoreDAO.deleteByItemIdAndItemCategoryAndIsThumbnailTrue(entity.getId(), ObjectType.EVENT);

            MediaStoreCreateModel thumbnailModel = eventUpdateModel.getNewThumbnailImage();
            thumbnailModel.setItemId(entity.getId());
            thumbnailModel.setItemCategory(ObjectType.EVENT.toString());
            thumbnailModel.setIsThumbnail(true);
            thumbnailModel.setCreated(LocalDateTime.now());
            thumbnailModel.setCreatedBy(user.getUsername());
            mediaService.create(thumbnailModel);
        }

        if (eventUpdateModel.getToAddImages() != null && !eventUpdateModel.getToAddImages().isEmpty()) {
            for (MediaStoreCreateModel imageModel : eventUpdateModel.getToAddImages()) {
                imageModel.setItemId(entity.getId());
                imageModel.setItemCategory(ObjectType.EVENT.toString());
                imageModel.setIsThumbnail(false);
                imageModel.setCreated(LocalDateTime.now());
                imageModel.setCreatedBy(user.getUsername());
                mediaService.create(imageModel);
            }
        }

        if (eventUpdateModel.getToRemoveImages() != null && !eventUpdateModel.getToRemoveImages().isEmpty()) {
            for (Integer imageId : eventUpdateModel.getToRemoveImages()) {
                Optional<MediaStoreEntity> imageEntity = mediaStoreDAO.findById(imageId);
                if (imageEntity.isPresent()) {
                    mediaStoreDAO.deleteById(imageId);
                }
            }
        }

        eventDAO.save(entity);

        return ResponseEntity.ok(eventMapper.entityToDto(entity));
    }

    @Override
    public ResponseEntity<EventModel> delete(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<EventOverviewModel> findEventOverview(String eventSlug) {
        if(eventSlug == null || eventSlug.isBlank()){
            throw new EventExceptions.EventNotFoundException(ExceptionCodes.EventExceptionCodes.EVENT_NOT_FOUND);
        }

        Optional<EventEntity> eventEntity = eventDAO.findBySlug(eventSlug);

        if(eventEntity.isEmpty()){
            throw new EventExceptions.EventNotFoundException(ExceptionCodes.EventExceptionCodes.EVENT_NOT_FOUND);
        } else {
            utils.setEventCategories(eventEntity.get());
            utils.setEventTags(eventEntity.get());
            utils.setEventOrganiserAndStats(eventEntity.get());

            EventOverviewModel eventOverviewModel = eventMapper.entityToOverviewDto(eventEntity.get());

            mediaService.lookupThumbnailImage(eventOverviewModel, ObjectType.EVENT, eventEntity.get().getId());
            mediaService.lookupAllImages(eventOverviewModel, ObjectType.EVENT, eventEntity.get().getId());

            return ResponseEntity.status(200).body(eventOverviewModel);
        }
    }

    @Override
    public ResponseEntity<Boolean> getEventAttendedStatus(Integer eventId, Principal principal) {
        if (eventId == null ) throw new EventExceptions.EventNotFoundException(ExceptionCodes.EventExceptionCodes.EVENT_NOT_FOUND);
        if (principal == null) throw new EventExceptions.EventUnauthorizedException(ExceptionCodes.EventExceptionCodes.EVENT_UNAUTHORIZED_ACCESS);

        Optional<EventEntity> eventEntity = eventDAO.findById(eventId);
        UserEntity userEntity = commonFunctions.getUserFromPrincipal(principal);

        if(eventEntity.isEmpty()){
            throw new EventExceptions.EventNotFoundException(ExceptionCodes.EventExceptionCodes.EVENT_NOT_FOUND);
        }

        boolean isAttended = utils.checkIfUserAttendedEvent(eventEntity.get().getId(), userEntity.getId());
        return ResponseEntity.status(200).body(isAttended);
    }

    @Override
    public ResponseEntity<List<EventShorthandModel>> findEventsForDate(LocalDate date) {
        if (date == null) {
            throw new EventExceptions.EventSystemException(ExceptionCodes.EventExceptionCodes.INVALID_REQUEST_CONTENT);
        }

        Specification<EventEntity> spec = Specification
                .allOf(EventSpecification.hasDate(date));

        List<EventEntity> eventEntities = eventDAO.findAll(spec);

        for (EventEntity eventEntity : eventEntities) {
            utils.setEventCategories(eventEntity);
            utils.setEventTags(eventEntity);
            mediaService.lookupThumbnailImage(eventEntity, ObjectType.EVENT, eventEntity.getId());
        }

        List<EventShorthandModel> response = eventMapper.entitiesToShorthandDtos(eventEntities);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<LocalDate, Boolean>> checkEventDatesForMonth(Integer year, Integer month) {
        if (year == null || month == null || month < 1 || month > 12) {
            throw new EventExceptions.EventSystemException(ExceptionCodes.EventExceptionCodes.INVALID_REQUEST_CONTENT);
        }

        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startOfMonth = yearMonth.atDay(1);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();

        Specification<EventEntity> spec = Specification
                .allOf(EventSpecification.hasDateRange(startOfMonth, endOfMonth));

        List<EventEntity> eventEntities = eventDAO.findAll(spec);

        Map<LocalDate, Boolean> result = new LinkedHashMap<>();

        for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
            LocalDate currentDate = yearMonth.atDay(day);
            boolean hasEvents = eventEntities.stream()
                    .anyMatch(event -> !event.getStartDate().toLocalDate().isAfter(currentDate)
                            && !event.getEndDate().toLocalDate().isBefore(currentDate));
            result.put(currentDate, hasEvents);
        }

        return ResponseEntity.ok(result);
    }
}
