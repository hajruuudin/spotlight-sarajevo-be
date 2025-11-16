package com.spotlightsarajevo.dao.dto.event;

import com.spotlightsarajevo.dao.dto.tag.TagModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "Properties for an event shorthand model")
public class EventShorthandModel implements Serializable {
    private static final long versionUUId = 1L;

    @Schema(description = "Unique identifier of the event")
    private Integer id;

    @Schema(description = "Unique slug identifier of the event")
    private String slug;

    @Schema(description = "Official name of the event")
    private String officialName;

    @Schema(description = "Small description/headline of the event in Bosnian")
    private String smallDescriptionBs;

    @Schema(description = "Small description/headline of the event in English")
    private String smallDescriptionEn;

    @Schema(description = "Category name for the event in bosnian")
    private String categoryNameBs;

    @Schema(description = "Category name for the event in english")
    private String categoryNameEn;

    @Schema(description = "The start date of the event")
    private LocalDateTime startDate;

    @Schema(description = "The thumbnail image of the event")
    private String thumbnailImage;

    @Schema(description = "Tags related to the event")
    private List<TagModel> eventTags;
}
