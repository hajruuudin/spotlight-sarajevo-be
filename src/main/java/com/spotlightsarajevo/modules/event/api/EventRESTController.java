package com.spotlightsarajevo.modules.event.api;

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
        System.out.println(sortOption + " A POR LA 15" + categoryIds);
        return eventService.findEventsPaginated(PageRequest.of(pageNumber, pageSize), searchTerm, sortOption, categoryIds);
    }
}
