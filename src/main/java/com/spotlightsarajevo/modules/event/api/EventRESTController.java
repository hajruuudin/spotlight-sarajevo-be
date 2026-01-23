package com.spotlightsarajevo.modules.event.api;

import com.spotlightsarajevo.modules.event.api.dto.EventOverviewModel;
import com.spotlightsarajevo.modules.event.api.dto.EventShorthandModel;
import com.spotlightsarajevo.modules.event.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@Tag(name = "event", description = "Event API")
@AllArgsConstructor
@RequestMapping(value = "/event")
public class EventRESTController {
    EventService eventService;

    @GetMapping(value = "/find-events")
    @Operation(description = "Retrieve a paginated result of events based on specific criteria")
    public ResponseEntity<Page<EventShorthandModel>> findEventsPaginated(
            @RequestParam(defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(defaultValue = "ALPHABETICAL", required = false) String sortOption,
            @RequestParam(required = false) List<Integer> categoryIds) {
        return eventService.findEventsPaginated(PageRequest.of(pageNumber, pageSize), searchTerm, sortOption, categoryIds);
    }

    @GetMapping(value = "/find-event-overview")
    @Operation(description = "Get an overview model for an event, including category names, tags and organiser model")
    public ResponseEntity<EventOverviewModel> findEventOverview(
            @RequestParam(required = true) String eventSlug
    ){
        return eventService.findEventOverview(eventSlug);
    }

    @GetMapping(value = "/attended-event/check")
    @Operation(description = "Check the status of the event and if the user has labeled it as attended/will attend")
    public ResponseEntity<Boolean> getEventStatus(
            @RequestParam(required = true) Integer eventId,
            Principal principal
    ){
        return eventService.getEventAttendedStatus(eventId, principal);
    }
}
