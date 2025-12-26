package com.spotlightsarajevo.modules.guide.service;

import com.spotlightsarajevo.modules.guide.api.dto.TouristGuideOverviewModel;
import com.spotlightsarajevo.modules.guide.api.dto.TouristGuideShorthandModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TouristGuideService {
    ResponseEntity<List<TouristGuideShorthandModel>> findAllGuides();
    ResponseEntity<TouristGuideOverviewModel> findGuideOverview(String slug);
}
