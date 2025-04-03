package ba.spotlightsarajevo.dao.models.review;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Schema(description = "Model for all properties of a review entity")
public class ReviewModel implements Serializable {
    @Serial
    public static final long versionUUId = 1L;

    @Schema(description = "Unique identifier")
    private Integer id;

    @Schema(description = "Identifier of the user")
    private Integer userId;

    @Schema(description = "Header or title")
    private String header;

    @Schema(description = "Main content")
    private String body;

    @Schema(description = "Overall rating")
    private Integer rating;

    @Schema(description = "Cleanliness score")
    private BigDecimal cleanliness;

    @Schema(description = "Affordability score")
    private BigDecimal affordability;

    @Schema(description = "Accessibility score")
    private BigDecimal accessibility;

    @Schema(description = "Staff kindness score")
    private BigDecimal staffKindness;

    @Schema(description = "Quality score")
    private BigDecimal quality;

    @Schema(description = "Atmosphere score")
    private BigDecimal atmosphere;

    @Schema(description = "Identifier of the spot")
    private Integer spotId;
}
