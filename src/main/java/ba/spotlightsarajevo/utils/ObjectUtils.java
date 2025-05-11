package ba.spotlightsarajevo.utils;

import ba.spotlightsarajevo.dao.*;
import ba.spotlightsarajevo.dao.entities.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
public class ObjectUtils {
    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter reverseDateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d");

    public void setSpotInformation(
            CategoryDAO categoryDAO,
            SpotReviewStatsDAO spotReviewStatsDAO,
            SpotTagDAO spotTagDAO,
            TagDAO tagDAO,
            SpotEntity entity
    ){
        /* SETTING THE SPOT CATEGORIES */
        Optional<CategoryEntity> categoryEntity = categoryDAO.findById(entity.getCategoryId());
        categoryEntity.ifPresent(category -> entity.setCategoryName(category.getCategoryName()));

        /* SETTING THE SPOT REVIEW */
        Optional<SpotReviewStatsEntity> spotReviewStatsEntity = spotReviewStatsDAO.findById(entity.getId());
        spotReviewStatsEntity.ifPresent(reviewStatsEntity -> entity.setRating(reviewStatsEntity.getCombinedRating()));

        /* SETTING THE SPOT TAGS */
        List<SpotTagEntity> spotTags = spotTagDAO.findAllTagsById(entity.getId());
        List<String> tagNames = new ArrayList<>();

        for(SpotTagEntity spotTag : spotTags){
            Optional<TagEntity> tag = tagDAO.findById(spotTag.getTagId());
            tag.ifPresent(tagEntity -> tagNames.add(tagEntity.getTagName()));
        }

        entity.setTagNames(tagNames);
    }

    public void setEventInformation(CategoryDAO categoryDAO, EventTagDAO eventTagDAO, TagDAO tagDAO, DateTimeFormatter reverseDateFormatter, EventEntity entity){
        /* SETTING THE EVENT CATEGORIES */
        Optional<CategoryEntity> categoryEntity = categoryDAO.findById(entity.getCategoryId());
        categoryEntity.ifPresent(category -> entity.setCategoryName(category.getCategoryName()));

        /* SETTING THE EVENT TAGS */
        List<EventTagEntity> eventTags = eventTagDAO.findAllTagsById(entity.getId());
        List<String> tagNames = new ArrayList<>();

        for(EventTagEntity eventTag : eventTags){
            Optional<TagEntity> tag = tagDAO.findById(eventTag.getTagId());
            tag.ifPresent(tagEntity -> tagNames.add(tagEntity.getTagName()));
        }

        /* FORMATTING THE EVENT DATE */
        LocalDateTime eventStartDate = entity.getStartDate();
        String formattedEventStartDate = eventStartDate.format(reverseDateFormatter);
        entity.setStartDateFormatted(formattedEventStartDate);

        entity.setTagNames(tagNames);
    }
}
