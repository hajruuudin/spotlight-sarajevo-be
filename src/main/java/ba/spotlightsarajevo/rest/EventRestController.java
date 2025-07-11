package ba.spotlightsarajevo.rest;

import ba.spotlightsarajevo.dao.models.event.EventCreate;
import ba.spotlightsarajevo.dao.models.event.EventModel;
import ba.spotlightsarajevo.dao.models.event.EventShorthand;
import ba.spotlightsarajevo.dao.models.event.EventUpdate;
import ba.spotlightsarajevo.dao.models.spot.SpotShorthand;
import ba.spotlightsarajevo.service.definition.service.EventService;
import ba.spotlightsarajevo.utils.SSEntityRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "Event", description = "Event API")
@RequestMapping(value = "/event")
@AllArgsConstructor
@RestController
public class EventRestController {
    EventService eventService;

    @Operation(description = "Gets a shorthand event(random spot) from the database")
    @GetMapping(value = "shorthand/headline")
    public ResponseEntity<EventShorthand> getEventHeadline(){
        return eventService.getEventHeadline();
    }

    @Operation(description = "Gets all the events from the database based on a provided date")
    @GetMapping(value = "{selectedQueryDate}")
    public ResponseEntity<Page<EventShorthand>> getEventsByDate(@PathVariable String selectedQueryDate){
        return eventService.getEventsByDate(PageRequest.of(0, 2), selectedQueryDate);
    }

    @Operation(description = "Get event shorthands from the database paginated based with search & filtering available")
    @GetMapping(value = "shorthand")
    public ResponseEntity<Page<EventShorthand>> getEventsShorthand(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "categories", required = false) List<Integer> categoryIds
    ){
        return eventService.getEventsShorthand(PageRequest.of(pageNumber, pageSize), search,  sort, categoryIds);
    }

    @Operation(description = "Gets an event overview")
    @GetMapping(value = "overview/{slug}")
    public ResponseEntity<EventModel> getEventOverview(@PathVariable String slug){
        return eventService.getEventOverview(new SSEntityRequest<>(slug));
    }

    /*======= ADMIN ROUTES =======*/
    @Operation(description = "Add a new event to the database")
    @PostMapping(value = "/admin")
    public ResponseEntity<EventModel> addEvent(@RequestBody EventCreate request, Principal principal){
        return eventService.create(request, principal);
    }

    @Operation(description = "Update an event object within the database")
    @PutMapping(value = "/admin")
    public ResponseEntity<EventModel> updateEvent(@RequestBody EventUpdate request, Principal principal){
        return eventService.updateEvent(request, principal);
    }
}
