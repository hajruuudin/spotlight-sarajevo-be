package ba.spotlightsarajevo.rest;

import ba.spotlightsarajevo.dao.entities.SampleEntity;
import ba.spotlightsarajevo.service.definition.service.SampleService;
import ba.spotlightsarajevo.utils.EntityRequest;
import ba.spotlightsarajevo.utils.EntityResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "sample", description = "Sample API")
@RequestMapping(value = "/sample")
@AllArgsConstructor
@RestController
public class SampleRestController {
    SampleService sampleService;

    @Operation(description = "Find a sample entity by its id")
    @GetMapping
    public EntityResponse<SampleEntity> sampleRoute(@Parameter(description = "ID of the sample", required = true) @RequestParam(required = true) Long id){
        return sampleService.findById(new EntityRequest<>(id));
    }
}
