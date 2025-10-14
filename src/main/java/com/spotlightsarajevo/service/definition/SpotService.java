package com.spotlightsarajevo.service.definition;

import com.spotlightsarajevo.dao.entities.SpotEntity;
import com.spotlightsarajevo.dao.models.spot.SpotCreateModel;
import com.spotlightsarajevo.dao.models.spot.SpotModel;
import com.spotlightsarajevo.dao.models.spot.SpotUpdateModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface SpotService {
    ResponseEntity<Page<SpotModel>> findAll(PageRequest pageRequest);

    ResponseEntity<SpotModel> create(SpotCreateModel spotCreateModel);

    ResponseEntity<SpotModel> update(SpotUpdateModel spotUpdateModel);

    ResponseEntity<SpotModel> delete(Integer id);

}
