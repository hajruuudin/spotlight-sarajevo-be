package com.spotlightsarajevo.modules.guide.api;

import com.spotlightsarajevo.modules.guide.api.dto.TouristGuideOverviewModel;
import com.spotlightsarajevo.modules.guide.api.dto.TouristGuideShorthandModel;
import com.spotlightsarajevo.modules.guide.service.TouristGuideService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@Tag(name = "guide", description = "Tourist Guide API")
@AllArgsConstructor
@RequestMapping(value = "/guide")
public class TouristGuideRESTController {

    TouristGuideService guideService;

    @GetMapping(value = "/all")
    @Operation(description = "Get all tourist guides from the system")
    public ResponseEntity<List<TouristGuideShorthandModel>> findAllGuides(){
        return guideService.findAllGuides();
    }

    @GetMapping(value = "/{slug}")
    @Operation(description = "Get a tourist guide by its ID")
    public ResponseEntity<TouristGuideOverviewModel> findGuideOverview(
            @PathVariable String slug)
    {
        return guideService.findGuideOverview(slug);
    }
}