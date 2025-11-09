package com.spotlightsarajevo.service.definition;

import com.spotlightsarajevo.dao.dto.spot.SpotCreateModel;
import com.spotlightsarajevo.dao.dto.spot.SpotModel;
import com.spotlightsarajevo.dao.dto.spot.SpotShorthandModel;
import com.spotlightsarajevo.dao.dto.spot.SpotUpdateModel;
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
