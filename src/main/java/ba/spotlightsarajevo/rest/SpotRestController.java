package ba.spotlightsarajevo.rest;

import ba.spotlightsarajevo.dao.models.spot.SpotCreate;
import ba.spotlightsarajevo.dao.models.spot.SpotModel;
import ba.spotlightsarajevo.service.definition.service.SpotService;
import ba.spotlightsarajevo.utils.EntityRequest;
import ba.spotlightsarajevo.utils.EntityResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Spot", description = "Sample API")
@RequestMapping(value = "/spot")
@AllArgsConstructor
@RestController
public class SpotRestController {
    SpotService spotService;

    @Operation(description = "Add a new spot to the system")
    @PostMapping
    public EntityResponse<SpotModel> create(@RequestBody SpotCreate request){
        return spotService.create(new EntityRequest<>(request));
    }
}
