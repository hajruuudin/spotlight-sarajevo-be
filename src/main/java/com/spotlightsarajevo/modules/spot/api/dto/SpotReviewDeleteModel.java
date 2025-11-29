package com.spotlightsarajevo.modules.spot.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "Properties for a spot review delete model")
public class SpotReviewDeleteModel implements Serializable {
    @Schema(description = "Id of the review object")
    private Integer id;

    @Schema(description = "Id of the user the review is associated with")
    private Integer userId;

    @Schema(description = "Id of the spot the review is associated with")
    private Integer spotId;
}
