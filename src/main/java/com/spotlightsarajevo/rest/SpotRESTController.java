package com.spotlightsarajevo.rest;

import com.spotlightsarajevo.dao.models.spot.SpotCreateModel;
import com.spotlightsarajevo.dao.models.spot.SpotModel;
import com.spotlightsarajevo.dao.models.spot.SpotUpdateModel;
import com.spotlightsarajevo.service.definition.SpotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
