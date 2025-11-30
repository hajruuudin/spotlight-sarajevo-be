package com.spotlightsarajevo.modules.spot.mapper;

import com.spotlightsarajevo.modules.spot.api.dto.SpotCreateModel;
import com.spotlightsarajevo.modules.spot.api.dto.SpotReviewCreateModel;
import com.spotlightsarajevo.modules.spot.api.dto.SpotReviewModel;
import com.spotlightsarajevo.modules.spot.api.dto.SpotReviewUpdateModel;
import com.spotlightsarajevo.modules.spot.domain.entity.SpotReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpotReviewMapper {
    List<SpotReviewModel> entitiesToDto(List<SpotReviewEntity> entity);
    SpotReviewEntity dtoToEntity(SpotReviewCreateModel dto);
    SpotReviewModel entityToDto(SpotReviewEntity entity);
    void updateEntityFromDto(SpotReviewUpdateModel dto, @MappingTarget SpotReviewEntity entity);
}
