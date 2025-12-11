package com.spotlightsarajevo.modules.event.service;

import com.spotlightsarajevo.modules.event.api.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EventService {
    ResponseEntity<Page<EventShorthandModel>> findEventsPaginated(PageRequest pageRequest, String searchTerm, String sortOption, List<Integer> categoryIds);
    ResponseEntity<EventModel> create(EventCreateModel eventCreateModel);
    ResponseEntity<EventModel> update(EventUpdateModel eventUpdateModel);
    ResponseEntity<EventModel> delete(Integer id);

    ResponseEntity<EventOverviewModel> findEventOverview(String eventSlug);
}
