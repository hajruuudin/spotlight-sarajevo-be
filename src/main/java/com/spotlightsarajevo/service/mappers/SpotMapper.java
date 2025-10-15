package com.spotlightsarajevo.service.mappers;

import com.spotlightsarajevo.dao.entity.SpotEntity;
import com.spotlightsarajevo.dao.dto.spot.SpotCreateModel;
import com.spotlightsarajevo.dao.dto.spot.SpotModel;
import com.spotlightsarajevo.dao.dto.spot.SpotUpdateModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpotMapper {
    SpotEntity dtoToEntity(SpotCreateModel spotCreateModel);
    SpotModel entityToDto(SpotEntity spotEntity);
    List<SpotModel> entitiesToDtos(List<SpotEntity> spotEntityList);
    void updateEntityFromDto(SpotUpdateModel spotUpdateModel, @MappingTarget SpotEntity spotEntity);
}
