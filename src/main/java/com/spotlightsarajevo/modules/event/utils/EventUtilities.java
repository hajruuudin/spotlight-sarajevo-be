package com.spotlightsarajevo.modules.event.utils;

import com.spotlightsarajevo.modules.category.domain.EventCategoryDAO;
import com.spotlightsarajevo.modules.category.domain.entity.EventCategoryEntity;
import com.spotlightsarajevo.modules.event.domain.EventTagsDAO;
import com.spotlightsarajevo.modules.event.domain.entity.EventEntity;
import com.spotlightsarajevo.modules.event.domain.entity.EventTagsEntity;
import com.spotlightsarajevo.modules.tag.domain.TagDAO;
import com.spotlightsarajevo.modules.tag.domain.entity.TagEntity;
import com.spotlightsarajevo.modules.tag.mappers.TagMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class EventUtilities {
    EventCategoryDAO eventCategoryDAO;
    EventTagsDAO eventTagsDAO;
    TagDAO tagDAO;
    TagMapper tagMapper;

    public void setEventCategories(EventEntity entity){
        Optional<EventCategoryEntity> category = eventCategoryDAO.findById(entity.getCategoryId());

        if (category.isPresent()) {
            entity.setCategoryNameBs(category.get().getEventCategoryNameBs());
            entity.setCategoryNameEn(category.get().getEventCategoryNameEn());
        }
    }

    public void setEventTags(EventEntity entity){
        List<TagEntity> tagEntities = new ArrayList<>();
        List<EventTagsEntity> eventTags = eventTagsDAO.findByEventId(entity.getId());

        for(EventTagsEntity eventTag : eventTags){
            Optional<TagEntity> tag = tagDAO.findById(eventTag.getTagId());

            if(tag.isPresent()){
                tagEntities.add(tag.get());
            } else {
                // Do some logging or something
            }
        }

        entity.setEventTags(tagMapper.entitiesToDtos(tagEntities));
    }
}
