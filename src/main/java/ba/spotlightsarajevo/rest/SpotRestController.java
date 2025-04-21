package ba.spotlightsarajevo.rest;

import ba.spotlightsarajevo.dao.models.spot.SpotCreate;
import ba.spotlightsarajevo.dao.models.spot.SpotModel;
import ba.spotlightsarajevo.dao.models.spot.SpotShorthand;
import ba.spotlightsarajevo.service.definition.service.SpotService;
import ba.spotlightsarajevo.utils.SSEntityRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Spot", description = "Sample API")
@RequestMapping(value = "/spot")
@AllArgsConstructor
@RestController
public class SpotRestController {
    SpotService spotService;

    @Operation(description = "Add a new spot to the system")
    @PostMapping
    public ResponseEntity<SpotModel> create(@RequestBody SpotCreate request){
        return spotService.create(new SSEntityRequest<SpotCreate>(request));
    }

    @Operation(description = "Get all spots from the database with pagination")
    @GetMapping
    public ResponseEntity<Page<SpotModel>> getSpotsPaginated(int pageNumber, int pageSize){
        return spotService.getSpotsPaginated(PageRequest.of(0, 10));
    }

    @Operation(description = "Gets a shorthand headline(random spot) from the database")
    @GetMapping(value = "shorthand/headline")
    public ResponseEntity<SpotShorthand> getSpotHeadline(){
        return spotService.getSpotHeadline();
    }

    @Operation(description = "Get spot by slug")
    @GetMapping(value = "{slug}")
    public ResponseEntity<SpotModel> findBySlug(@RequestParam String slug){
        return null;
    }

    @Operation(description = "Delete a spot from the database")
    @DeleteMapping("/admin/{id}")
    public void deleteSpot(@RequestParam Long id){

    }
}
