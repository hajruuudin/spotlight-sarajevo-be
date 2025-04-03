package ba.spotlightsarajevo.dao.models.spot;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "Model for all properties of a spot entity")
public class SpotModel {
    @Serial
    private static final long versionUUId = 1L;

    @Schema(description = "Unique identifier")
    private Integer id;

    @Schema(description = "URL-friendly identifier")
    private String slug;

    @Schema(description = "Official name")
    private String officialName;

    @Schema(description = "Short description")
    private String smallDescription;

    @Schema(description = "Full description")
    private String fullDescription;

    @Schema(description = "Overall rating")
    private BigDecimal rating;

    @Schema(description = "Latitude coordinate")
    private BigDecimal latitude;

    @Schema(description = "Longitude coordinate")
    private BigDecimal longitude;

    @Schema(description = "Identifier of the category")
    private Integer categoryId;

    @Schema(description = "Initial cleanliness score placed by the admin")
    private BigDecimal cleanliness;

    @Schema(description = "Initial affordability score placed by the admin")
    private BigDecimal affordability;

    @Schema(description = "Initial accessibility score placed by the admin")
    private BigDecimal accessibility;

    @Schema(description = "Initial staff kindness score placed by the admin")
    private BigDecimal staffKindness;

    @Schema(description = "Initial quality score placed by the admin")
    private BigDecimal quality;

    @Schema(description = "Initial atmosphere score placed by the admin")
    private BigDecimal atmosphere;

    @Schema(description = "Creation timestamp")
    private LocalDateTime created;

    @Schema(description = "User who created the record")
    private String createdBy;

    @Schema(description = "Last modification timestamp")
    private LocalDateTime modified;

    @Schema(description = "User who last modified the record")
    private String modifiedBy;
}
