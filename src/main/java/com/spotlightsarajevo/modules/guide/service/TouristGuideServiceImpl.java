package com.spotlightsarajevo.modules.guide.service;

import com.spotlightsarajevo.common.enums.ExceptionCodes;
import com.spotlightsarajevo.common.exceptions.TouristGuideExceptions;
import com.spotlightsarajevo.modules.guide.api.dto.TouristGuideOverviewModel;
import com.spotlightsarajevo.modules.guide.api.dto.TouristGuideSectionModel;
import com.spotlightsarajevo.modules.guide.api.dto.TouristGuideShorthandModel;
import com.spotlightsarajevo.modules.guide.domain.TouristGuideDAO;
import com.spotlightsarajevo.modules.guide.domain.TouristGuideSectionDAO;
import com.spotlightsarajevo.modules.guide.domain.entity.TouristGuideEntity;
import com.spotlightsarajevo.modules.guide.domain.entity.TouristGuideSectionEntity;
import com.spotlightsarajevo.modules.guide.mapper.TouristGuideMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TouristGuideServiceImpl implements TouristGuideService {
    TouristGuideDAO touristGuideDAO;
    TouristGuideSectionDAO touristGuideSectionDAO;
    TouristGuideMapper touristGuideMapper;

    @Override
    public ResponseEntity<List<TouristGuideShorthandModel>> findAllGuides() {
        List<TouristGuideEntity> entities = touristGuideDAO.findAll();

        List<TouristGuideShorthandModel> models = touristGuideMapper.entitiesToShorthandDtos(entities);

        return ResponseEntity.status(200).body(models);
    }

    @Override
    public ResponseEntity<TouristGuideOverviewModel> findGuideOverview(String slug) {
        if(slug == null) throw new TouristGuideExceptions.TouristGuideRequestException(ExceptionCodes.TouristGuideExceptionCodes.TOURIST_GUIDE_BAD_REQUEST);

        Optional<TouristGuideEntity> entityOptional = touristGuideDAO.findBySlug(slug);
        TouristGuideEntity entity = entityOptional.orElseThrow(
                () -> new TouristGuideExceptions.TouristGuideRequestException(ExceptionCodes.TouristGuideExceptionCodes.TOURIST_GUIDE_NOT_FOUND)
        );

        TouristGuideOverviewModel overviewModel = touristGuideMapper.entityToOverviewDto(entity);

        List<TouristGuideSectionEntity> sectionEntities = touristGuideSectionDAO.findAllById(Collections.singleton(entity.getId()));
        List<TouristGuideSectionModel> sectionModels = new ArrayList<>();

        for(TouristGuideSectionEntity section : sectionEntities){
            TouristGuideSectionModel model = touristGuideMapper.sectionEntityToDto(section);
            sectionModels.add(model);
        }

        overviewModel.setSections(sectionModels);

        return ResponseEntity.status(200).body(overviewModel);
    }
}
