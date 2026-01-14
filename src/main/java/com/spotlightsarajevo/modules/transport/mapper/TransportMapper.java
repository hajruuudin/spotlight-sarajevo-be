package com.spotlightsarajevo.modules.transport.mapper;

import com.spotlightsarajevo.modules.transport.api.dto.TransportMethodLineModel;
import com.spotlightsarajevo.modules.transport.api.dto.TransportMethodModel;
import com.spotlightsarajevo.modules.transport.api.dto.TransportMethodShorthandModel;
import com.spotlightsarajevo.modules.transport.domain.entity.TransportMethodEntity;
import com.spotlightsarajevo.modules.transport.domain.entity.TransportMethodLineEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransportMapper {
    TransportMethodShorthandModel entityToShorthandDto(TransportMethodEntity entity);
    List<TransportMethodShorthandModel> entitiesToShorthandDtos(List<TransportMethodEntity> entities);

    @Mapping(target = "geometryGeoJson", ignore = true)
    TransportMethodModel entityToDto(TransportMethodEntity entity);

    @Mapping(source = "operator.id", target = "operatorId")
    @Mapping(source = "transportType.id", target = "transportTypeId")
    @Mapping(target = "geometryGeoJson", ignore = true)
    TransportMethodLineModel lineEntityToDto(TransportMethodLineEntity entity);

    List<TransportMethodLineModel> lineEntitiesToDtos(List<TransportMethodLineEntity> entities);
}
