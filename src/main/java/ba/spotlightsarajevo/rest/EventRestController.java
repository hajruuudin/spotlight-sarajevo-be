package ba.spotlightsarajevo.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Event", description = "Event API")
@RequestMapping(value = "/event")
@AllArgsConstructor
@RestController
public class EventRestController {
    /*
    * - Get All Events
    * - Get all events with specific filters (could be split up into different routes)
    * - Create event for admin, including all intermediary tables (tags)
    * - Update event for admin, including all intermediary tables (tags, if changed)
    * - Delete event
    * - Mark event as attended/will attend for a userID
    *
    */
}
