package com.spotlightsarajevo.service.implementation;

import com.spotlightsarajevo.dao.dto.event.EventCreateModel;
import com.spotlightsarajevo.dao.dto.event.EventModel;
import com.spotlightsarajevo.dao.dto.event.EventUpdateModel;
import com.spotlightsarajevo.service.definition.EventService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@Service
public class EventServiceImpl implements EventService {
    @Override
    public ResponseEntity<Page<EventModel>> findAll(Pageable pageable) {
        return null;
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
