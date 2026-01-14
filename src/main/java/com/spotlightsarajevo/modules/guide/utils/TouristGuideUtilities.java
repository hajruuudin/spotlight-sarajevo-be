package com.spotlightsarajevo.modules.guide.utils;

import com.spotlightsarajevo.modules.category.domain.entity.TouristGuideCategoryEntity;
import com.spotlightsarajevo.modules.guide.domain.TouristGuideCategoryDAO;
import com.spotlightsarajevo.modules.guide.domain.entity.TouristGuideEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class TouristGuideUtilities {
    TouristGuideCategoryDAO touristGuideCategoryDAO;

    public void setGuideCategoryNames(TouristGuideEntity entity){
        Optional<TouristGuideCategoryEntity> category = touristGuideCategoryDAO.findById(entity.getCategoryId());

        if(category.isPresent()){
           entity.setCategoryNameEn(category.get().getCategoryNameEn());
           entity.setCategoryNameBs(category.get().getCategoryNameBs());
        }
    }
}
