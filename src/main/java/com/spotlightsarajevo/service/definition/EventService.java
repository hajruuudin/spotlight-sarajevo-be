package com.spotlightsarajevo.service.definition;

import com.spotlightsarajevo.dao.dto.event.EventCreateModel;
import com.spotlightsarajevo.dao.dto.event.EventModel;
import com.spotlightsarajevo.dao.dto.event.EventShorthandModel;
import com.spotlightsarajevo.dao.dto.event.EventUpdateModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.awt.print.Pageable;
import java.util.List;

public interface EventService {
    ResponseEntity<Page<EventShorthandModel>> findEventsPaginated(PageRequest pageRequest, String searchTerm, String sortOption, List<Integer> categoryIds);
    ResponseEntity<EventModel> create(EventCreateModel eventCreateModel);
    ResponseEntity<EventModel> update(EventUpdateModel eventUpdateModel);
    ResponseEntity<EventModel> delete(Integer id);
}
