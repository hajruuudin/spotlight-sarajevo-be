package ba.spotlightsarajevo.rest;

import ba.spotlightsarajevo.dao.models.event.EventShorthand;
import ba.spotlightsarajevo.dao.models.spot.SpotShorthand;
import ba.spotlightsarajevo.service.definition.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Event", description = "Event API")
@RequestMapping(value = "/event")
@AllArgsConstructor
@RestController
public class EventRestController {
    EventService eventService;
    /*
    * - Get All Events
    * - Get all events with specific filters (could be split up into different routes)
    * - Create event for admin, including all intermediary tables (tags)
    * - Update event for admin, including all intermediary tables (tags, if changed)
    * - Delete event
    * - Mark event as attended/will attend for a userID
    *
    */
    @Operation(description = "Gets a shorthand event(random spot) from the database")
    @GetMapping(value = "shorthand/headline")
    public ResponseEntity<EventShorthand> getEventHeadline(){
        return eventService.getEventHeadline();
    }
}
