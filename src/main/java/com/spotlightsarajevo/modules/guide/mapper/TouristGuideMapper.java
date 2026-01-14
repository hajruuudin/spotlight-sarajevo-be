package com.spotlightsarajevo.modules.guide.mapper;

import com.spotlightsarajevo.modules.guide.api.dto.*;
import com.spotlightsarajevo.modules.guide.domain.entity.TouristGuideEntity;
import com.spotlightsarajevo.modules.guide.domain.entity.TouristGuideSectionEntity;
import org.mapstruct.Mapper;
import org.w3c.dom.Entity;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TouristGuideMapper {
    TouristGuideShorthandModel entityToShorthandDto(TouristGuideEntity entity);
    List<TouristGuideShorthandModel> entitiesToShorthandDtos(List<TouristGuideEntity> entities);
    TouristGuideOverviewModel entityToOverviewDto(TouristGuideEntity entity);
    TouristGuideEntity dtoToEntity(TouristGuideCreateModel dto);

    TouristGuideSectionModel sectionEntityToDto(TouristGuideSectionEntity entity);
    TouristGuideSectionEntity dtoToSectionEntity(TouristGuideSectionCreateModel dto);
}
