package com.spotlightsarajevo.service.definition;

import com.spotlightsarajevo.dao.dto.event.EventCreateModel;
import com.spotlightsarajevo.dao.dto.event.EventModel;
import com.spotlightsarajevo.dao.dto.event.EventUpdateModel;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.awt.print.Pageable;

public interface EventService {
    ResponseEntity<Page<EventModel>> findAll(Pageable pageable);
    ResponseEntity<EventModel> create(EventCreateModel eventCreateModel);
    ResponseEntity<EventModel> update(EventUpdateModel eventUpdateModel);
    ResponseEntity<EventModel> delete(Integer id);
}
