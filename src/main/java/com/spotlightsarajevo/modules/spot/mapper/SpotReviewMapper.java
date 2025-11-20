package com.spotlightsarajevo.modules.spot.mapper;

import com.spotlightsarajevo.modules.spot.api.dto.SpotReviewModel;
import com.spotlightsarajevo.modules.spot.domain.entity.SpotReviewEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpotReviewMapper {
    List<SpotReviewModel> entitiesToDto(List<SpotReviewEntity> entity);
}
