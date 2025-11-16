package com.spotlightsarajevo.rest;

import com.spotlightsarajevo.dao.dto.spot.SpotCreateModel;
import com.spotlightsarajevo.dao.dto.spot.SpotModel;
import com.spotlightsarajevo.dao.dto.spot.SpotShorthandModel;
import com.spotlightsarajevo.dao.dto.spot.SpotUpdateModel;
import com.spotlightsarajevo.service.definition.SpotService;
import com.spotlightsarajevo.utils.enums.SortingOptions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "spot", description = "Spot API")
@AllArgsConstructor
@RequestMapping(value = "/spot")
public class SpotRESTController {
    SpotService spotService;

    @GetMapping
    @Operation(description = "Retrieve all spots from the database paginated")
    public ResponseEntity<Page<SpotModel>> findAll(){
        return spotService.findAll(PageRequest.of(0, 10));
    }

    @GetMapping(value = "/find-spots")
    @Operation(description = "Retrieve a paginated result of spots based on specific criteria")
    public ResponseEntity<Page<SpotShorthandModel>> findSpotsPaginated(
            @RequestParam(defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(defaultValue = "ALPHABETICAL", required = false) String sortOption,
            @RequestParam(required = false) List<Integer> categoryIds) {
        System.out.println(sortOption + " A POR LA 15" + categoryIds);
        return spotService.findSpotsPaginated(PageRequest.of(pageNumber, pageSize), searchTerm, sortOption, categoryIds);
    }

    @PostMapping
    @Operation(description = "Add a spot to the database")
    public ResponseEntity<SpotModel> create(SpotCreateModel request){
        return spotService.create(request);
    }

    @PutMapping
    @Operation(description = "Update a spot from the database")
    public ResponseEntity<SpotModel> update(SpotUpdateModel request){
        return spotService.update(request);
    }

    @DeleteMapping
    @Operation(description = "Delete a spot from the database")
    public ResponseEntity<SpotModel> delete(Integer request){
        return spotService.delete(request);
    }
}
