package ba.spotlightsarajevo.rest;

import ba.spotlightsarajevo.dao.models.event.EventShorthand;
import ba.spotlightsarajevo.dao.models.spot.SpotShorthand;
import ba.spotlightsarajevo.service.definition.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
