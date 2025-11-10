package com.spotlightsarajevo.dao.dto.spot;

import com.spotlightsarajevo.dao.dto.tag.TagModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Data
@Schema(description = "Properties for a spot shorthand model")
public class SpotShorthandModel implements Serializable {
    private static final long versionUUId = 1L;

    @Schema(description = "Unique identifier of the Spot")
    private Integer id;

    @Schema(description = "Unique slug identifier of the spot")
    private String slug;

    @Schema(description = "Official name of the spot")
    private String officialName;

    @Schema(description = "Small description/headline of the spot in Bosnian")
    private String smallDescriptionBs;

    @Schema(description = "Small description/headline of the spot in English")
    private String smallDescriptionEn;

    @Schema(description = "Category name for the spot in bosnian")
    private String categoryNameBs;

    @Schema(description = "Category name for the spot in english")
    private String categoryNameEn;

    @Schema(description = "The average rating of the spot taken from the spot review stats view")
    private BigDecimal combinedRating;

    @Schema(description = "The thumbnail image of the spot")
    private String thumbnailImage;

    @Schema(description = "Tags related to the spot")
    private List<TagModel> spotTags;
}
