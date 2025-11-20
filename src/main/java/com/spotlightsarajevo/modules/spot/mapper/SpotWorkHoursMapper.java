package com.spotlightsarajevo.modules.spot.mapper;

import com.spotlightsarajevo.modules.spot.api.dto.SpotWorkHoursModel;
import com.spotlightsarajevo.modules.spot.domain.entity.SpotWorkHoursEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpotWorkHoursMapper {
    List<SpotWorkHoursModel> entitiesToDtos(List<SpotWorkHoursEntity> entities);
}
