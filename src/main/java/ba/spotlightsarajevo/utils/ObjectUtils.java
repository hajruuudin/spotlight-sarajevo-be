package ba.spotlightsarajevo.utils;

import ba.spotlightsarajevo.dao.*;
import ba.spotlightsarajevo.dao.entities.*;
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

    public void setSpotShorthandInformation(
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

    public void setSpotFullInformation(
            CategoryDAO categoryDAO,
            SpotReviewStatsDAO spotReviewStatsDAO,
            SpotReviewDAO spotReviewDAO,
            SpotTagDAO spotTagDAO,
            SpotWorkHoursDAO spotWorkHoursDAO,
            TagDAO tagDAO,
            ReviewDAO reviewDAO,
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

        /* SETTING THE SPOT REVIEWS */
        List<SpotReviewEntity> spotReviews = spotReviewDAO.findAllBySpotId(entity.getId());
        List<ReviewEntity> reviews = new ArrayList<>();

        for(SpotReviewEntity spotReview : spotReviews){
            Optional<ReviewEntity> review = reviewDAO.findById(spotReview.getId());
            review.ifPresent(reviewEntity -> reviews.add(reviewEntity));
        }

        entity.setReviews(reviews);

        /* SETTING THE SPOT WORK HOURS */
        List<SpotWorkHoursEntity> spotWorkHoursEntityList = spotWorkHoursDAO.findAllBySpotId(entity.getId());
        entity.setWorkHours(spotWorkHoursEntityList);
    }

    public void setEventShorthandInformation(CategoryDAO categoryDAO, EventTagDAO eventTagDAO, TagDAO tagDAO, DateTimeFormatter reverseDateFormatter, EventEntity entity){
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
