package ba.spotlightsarajevo.utils;

import ba.spotlightsarajevo.dao.*;
import ba.spotlightsarajevo.dao.entities.*;
import ba.spotlightsarajevo.dao.models.spot.SpotUpdate;
import ba.spotlightsarajevo.dao.models.tag.TagUpdateModel;
import ba.spotlightsarajevo.enums.WeekDay;
import org.springframework.context.annotation.Configuration;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    public void formatSpotUpdate(
            SpotUpdate update,
            SpotEntity entity,
            UserEntity adminUser,
            CategoryDAO categoryDAO,
            TagDAO tagDAO,
            SpotTagDAO spotTagDAO,
            SpotWorkHoursDAO spotWorkHoursDAO
    ){
        /* NO FORMAT INFORMATION */
        entity.setSlug(update.getSlug());
        entity.setOfficialName(update.getOfficialName());
        entity.setSmallDescription(update.getSmallDescription());
        entity.setFullDescription(update.getFullDescription());
        entity.setLatitude(update.getLat());
        entity.setLongitude(update.getLon());
        entity.setAddress(update.getAddress());

        entity.setCleanliness(update.getCleanliness());
        entity.setAffordability(update.getAffordability());
        entity.setAccessibility(update.getAccessibility());
        entity.setStaffKindness(update.getStaffKindness());
        entity.setQuality(update.getOverallQuality());
        entity.setAtmosphere(update.getAtmosphere());

        entity.setModified(LocalDateTime.now());
        entity.setModifiedBy(adminUser.getEmail());

        /* UPDATING THE CATEGORY NAME */
        Optional<CategoryEntity> categoryEntity = categoryDAO.findByCategoryName(update.getCategoryName());
        categoryEntity.ifPresent(category -> entity.setCategoryId(category.getId()));

        System.out.println(update);
        /* UPDATING THE TAG NAME / SEPARATE TABLE ACTION */
        spotTagDAO.deleteAllBySpotId(update.getId());
        for (TagUpdateModel element : update.getTags()){
            Optional<TagEntity> tagEntity = tagDAO.findByTagName(element.getTagName());
            tagEntity.ifPresent(tag -> {
                SpotTagEntity spotTagEntity = new SpotTagEntity();
                spotTagEntity.setTagId(tag.getId());
                spotTagEntity.setSpotId(entity.getId());
                spotTagDAO.save(spotTagEntity);
            });
        }

        /* UPDATING THE WORK HOURS / SEPARATE TABLE ACTION */
        spotWorkHoursDAO.deleteAllBySpotId(entity.getId());

        if (update.getMondayStartTime() != null && update.getMondayEndTime() != null &&
                !update.getMondayStartTime().isEmpty() && !update.getMondayEndTime().isEmpty()) {
            SpotWorkHoursEntity monday = new SpotWorkHoursEntity();
            monday.setDay(WeekDay.MON);
            monday.setSpotId(entity.getId());
            monday.setStartTime(LocalTime.parse(update.getMondayStartTime()));
            monday.setEndTime(LocalTime.parse(update.getMondayEndTime()));
            spotWorkHoursDAO.save(monday);
        }

        if (update.getTuesdayStartTime() != null && update.getTuesdayEndTime() != null &&
                !update.getTuesdayStartTime().isEmpty() && !update.getTuesdayEndTime().isEmpty()) {
            SpotWorkHoursEntity tuesday = new SpotWorkHoursEntity();
            tuesday.setDay(WeekDay.TUE);
            tuesday.setSpotId(entity.getId());
            tuesday.setStartTime(LocalTime.parse(update.getTuesdayStartTime()));
            tuesday.setEndTime(LocalTime.parse(update.getTuesdayEndTime()));
            spotWorkHoursDAO.save(tuesday);
        }

        if (update.getWednesdayStartTime() != null && update.getWednesdayEndTime() != null &&
                !update.getWednesdayStartTime().isEmpty() && !update.getWednesdayEndTime().isEmpty()) {
            SpotWorkHoursEntity wednesday = new SpotWorkHoursEntity();
            wednesday.setDay(WeekDay.WED);
            wednesday.setSpotId(entity.getId());
            wednesday.setStartTime(LocalTime.parse(update.getWednesdayStartTime()));
            wednesday.setEndTime(LocalTime.parse(update.getWednesdayEndTime()));
            spotWorkHoursDAO.save(wednesday);
        }

        if (update.getThursdayStartTime() != null && update.getThursdayEndTime() != null &&
                !update.getThursdayStartTime().isEmpty() && !update.getThursdayEndTime().isEmpty()) {
            SpotWorkHoursEntity thursday = new SpotWorkHoursEntity();
            thursday.setDay(WeekDay.THU);
            thursday.setSpotId(entity.getId());
            thursday.setStartTime(LocalTime.parse(update.getThursdayStartTime()));
            thursday.setEndTime(LocalTime.parse(update.getThursdayEndTime()));
            spotWorkHoursDAO.save(thursday);
        }

        if (update.getFridayStartTime() != null && update.getFridayEndTime() != null &&
                !update.getFridayStartTime().isEmpty() && !update.getFridayEndTime().isEmpty()) {
            SpotWorkHoursEntity friday = new SpotWorkHoursEntity();
            friday.setDay(WeekDay.FRI);
            friday.setSpotId(entity.getId());
            friday.setStartTime(LocalTime.parse(update.getFridayStartTime()));
            friday.setEndTime(LocalTime.parse(update.getFridayEndTime()));
            spotWorkHoursDAO.save(friday);
        }

        if (update.getSaturdayStartTime() != null && update.getSaturdayEndTime() != null &&
                !update.getSaturdayStartTime().isEmpty() && !update.getSaturdayEndTime().isEmpty()) {
            SpotWorkHoursEntity saturday = new SpotWorkHoursEntity();
            saturday.setDay(WeekDay.SAT);
            saturday.setSpotId(entity.getId());
            saturday.setStartTime(LocalTime.parse(update.getSaturdayStartTime()));
            saturday.setEndTime(LocalTime.parse(update.getSaturdayEndTime()));
            spotWorkHoursDAO.save(saturday);
        }

        if (update.getSundayStartTime() != null && update.getSundayEndTime() != null &&
                !update.getSundayStartTime().isEmpty() && !update.getSundayEndTime().isEmpty()) {
            SpotWorkHoursEntity sunday = new SpotWorkHoursEntity();
            sunday.setDay(WeekDay.SUN);
            sunday.setSpotId(entity.getId());
            sunday.setStartTime(LocalTime.parse(update.getSundayStartTime()));
            sunday.setEndTime(LocalTime.parse(update.getSundayEndTime()));
            spotWorkHoursDAO.save(sunday);
        }

    }

    public void setEventInformation(CategoryDAO categoryDAO, EventTagDAO eventTagDAO, TagDAO tagDAO, EventEntity entity){
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
