package ba.spotlightsarajevo.dao.models.spotreviewstats;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Schema(description = "Model for all properties of the spot review stats view")
public class SpotReviewStatsModel implements Serializable {
    @Serial
    private static final long versionUUId = 1L;

    @Schema(description = "Unique identifier of the spot")
    private Integer spotId;

    @Schema(description = "Combined cleanliness of the initial admin rating and all reviews combined")
    private BigDecimal combinedCleanliness;

    @Schema(description = "Combined affordability of the initial admin rating and all reviews combined")
    private BigDecimal combinedAffordability;

    @Schema(description = "Combined accessibility of the initial admin rating and all reviews combined")
    private BigDecimal combinedAccessibility;

    @Schema(description = "Combined staff kindness of the initial admin rating and all reviews combined")
    private BigDecimal combinedStaffKindness;

    @Schema(description = "Combined quality of the initial admin rating and all reviews combined")
    private BigDecimal combinedQuality;

    @Schema(description = "Combined atmosphere of the initial admin rating and all reviews combined")
    private BigDecimal combinedAtmosphere;
}
