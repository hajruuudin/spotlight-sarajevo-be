package ba.spotlightsarajevo.service.definition.service;

import ba.spotlightsarajevo.dao.models.event.EventCreate;
import ba.spotlightsarajevo.dao.models.event.EventModel;
import ba.spotlightsarajevo.dao.models.event.EventShorthand;
import ba.spotlightsarajevo.utils.SSEntityRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EventService {
    ResponseEntity<EventModel> create(final SSEntityRequest<EventCreate> request);

    ResponseEntity<Page<EventShorthand>> getEventsShorthand(PageRequest request, String search, String sort, List<Integer> categoryIds);

    ResponseEntity<EventShorthand> getEventHeadline();

    ResponseEntity<Page<EventShorthand>> getEventsByDate(PageRequest request, String date);

    ResponseEntity<EventModel> getEventOverview(SSEntityRequest<String>request);
}
