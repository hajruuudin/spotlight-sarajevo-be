package com.spotlightsarajevo.modules.guide.service;

import com.spotlightsarajevo.modules.guide.api.dto.TouristGuideCreateModel;
import com.spotlightsarajevo.modules.guide.api.dto.TouristGuideOverviewModel;
import com.spotlightsarajevo.modules.guide.api.dto.TouristGuideShorthandModel;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

public interface TouristGuideService {
    ResponseEntity<List<TouristGuideShorthandModel>> findAllGuides();
    ResponseEntity<List<TouristGuideShorthandModel>> findByCategory(Integer categoryId);
    ResponseEntity<TouristGuideOverviewModel> findGuideOverview(String slug);
    ResponseEntity<TouristGuideOverviewModel> create(TouristGuideCreateModel request, Principal principal);
}
