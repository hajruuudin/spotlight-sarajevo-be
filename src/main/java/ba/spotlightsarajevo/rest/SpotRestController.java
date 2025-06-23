package ba.spotlightsarajevo.rest;

import ba.spotlightsarajevo.dao.models.spot.SpotCreate;
import ba.spotlightsarajevo.dao.models.spot.SpotModel;
import ba.spotlightsarajevo.dao.models.spot.SpotShorthand;
import ba.spotlightsarajevo.dao.models.spot.SpotUpdate;
import ba.spotlightsarajevo.service.definition.service.SpotService;
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

    @Operation(description = "Get spot shorthands from the database with pagination and an optional filtering (using search, sort and categories)")
    @GetMapping(value = "shorthands")
    public ResponseEntity<Page<SpotShorthand>> getSpotsShorthand(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "categories", required = false) List<Integer> categoryIds
            ){
        return spotService.getSpotsShorthand(PageRequest.of(pageNumber, pageSize), search, sort, categoryIds);
    }

    @Operation(description = "Gets a shorthand headline(random spot) from the database")
    @GetMapping(value = "shorthand/headline")
    public ResponseEntity<SpotShorthand> getSpotHeadline(){
        return spotService.getSpotHeadline();
    }

    @Operation(description = "Get spots based on a certain category")
    @GetMapping(value = "category/{categoryId}")
    public ResponseEntity<Page<SpotShorthand>> getCategorisedSpots(@PathVariable Integer categoryId){
        return spotService.getCategorisedSpots(PageRequest.of(0, 3),categoryId);
    }

    @Operation(description = "Get spot overview by slug")
    @GetMapping(value = "overview/{slug}")
    public ResponseEntity<SpotModel> getSpotOverviewBySlug(@PathVariable String slug){
        return spotService.getSpotOverviewBySlug(new SSEntityRequest<>(slug));
    }

    /**=========== ADMIN ROUTES ============**/

    @Operation(description = "Delete a spot from the database")
    @DeleteMapping("/admin/{id}")
    public void deleteSpot(@RequestParam Long id){

    }

    @Operation(description = "Update a spot from the database")
    @PutMapping(value = "/admin")
    public ResponseEntity<SpotModel> updateSpot(@RequestBody SpotUpdate request, Principal principal){
        System.out.println("THIS IS BEING CALLED");
        return spotService.updateSpot(request, principal);
    }
}
