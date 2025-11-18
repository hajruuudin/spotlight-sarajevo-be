package com.spotlightsarajevo.modules.spot.service;

import com.spotlightsarajevo.modules.spot.api.dto.SpotCreateModel;
import com.spotlightsarajevo.modules.spot.api.dto.SpotModel;
import com.spotlightsarajevo.modules.spot.api.dto.SpotShorthandModel;
import com.spotlightsarajevo.modules.spot.api.dto.SpotUpdateModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SpotService {
    ResponseEntity<Page<SpotModel>> findAll(PageRequest pageRequest);

    ResponseEntity<Page<SpotShorthandModel>> findSpotsPaginated(PageRequest pageRequest, String searchTerm, String sortOption, List<Integer> categoryIds);

    ResponseEntity<SpotModel> create(SpotCreateModel spotCreateModel);

    ResponseEntity<SpotModel> update(SpotUpdateModel spotUpdateModel);

    ResponseEntity<SpotModel> delete(Integer id);

}
