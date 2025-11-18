package com.spotlightsarajevo.modules.spot.mapper;

import com.spotlightsarajevo.modules.spot.api.dto.SpotShorthandModel;
import com.spotlightsarajevo.modules.spot.domain.entity.SpotEntity;
import com.spotlightsarajevo.modules.spot.api.dto.SpotCreateModel;
import com.spotlightsarajevo.modules.spot.api.dto.SpotModel;
import com.spotlightsarajevo.modules.spot.api.dto.SpotUpdateModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpotMapper {
    SpotEntity dtoToEntity(SpotCreateModel spotCreateModel);
    SpotModel entityToDto(SpotEntity spotEntity);
    List<SpotModel> entitiesToDtos(List<SpotEntity> spotEntityList);
    void updateEntityFromDto(SpotUpdateModel spotUpdateModel, @MappingTarget SpotEntity spotEntity);
    List<SpotShorthandModel> entitiesToShorthandDtos(List<SpotEntity> entities);
}
