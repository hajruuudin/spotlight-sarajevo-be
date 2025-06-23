package ba.spotlightsarajevo.dao.models.spot;

import ba.spotlightsarajevo.dao.models.tag.TagUpdateModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "Properties of a spot entity update request")
public class SpotUpdate implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier")
    private Integer id;

    @Schema(description = "Official name")
    private String officialName;

    @Schema(description = "URL-friendly identifier")
    private String slug;

    @Schema(description = "Category name")
    private String categoryName;

    @Schema(description = "Spot address")
    private String address;

    @Schema(description = "Latitude coordinate")
    private BigDecimal lat;

    @Schema(description = "Longitude coordinate")
    private BigDecimal lon;

    @Schema(description = "Full description")
    private String fullDescription;

    @Schema(description = "Short description")
    private String smallDescription;

    @Schema(description = "Monday start time")
    private String mondayStartTime;

    @Schema(description = "Monday end time")
    private String mondayEndTime;

    @Schema(description = "Tuesday start time")
    private String tuesdayStartTime;

    @Schema(description = "Tuesday end time")
    private String tuesdayEndTime;

    @Schema(description = "Wednesday start time")
    private String wednesdayStartTime;

    @Schema(description = "Wednesday end time")
    private String wednesdayEndTime;

    @Schema(description = "Thursday start time")
    private String thursdayStartTime;

    @Schema(description = "Thursday end time")
    private String thursdayEndTime;

    @Schema(description = "Friday start time")
    private String fridayStartTime;

    @Schema(description = "Friday end time")
    private String fridayEndTime;

    @Schema(description = "Saturday start time")
    private String saturdayStartTime;

    @Schema(description = "Saturday end time")
    private String saturdayEndTime;

    @Schema(description = "Sunday start time")
    private String sundayStartTime;

    @Schema(description = "Sunday end time")
    private String sundayEndTime;

    @Schema(description = "Affordability score")
    private BigDecimal affordability;

    @Schema(description = "Atmosphere score")
    private BigDecimal atmosphere;

    @Schema(description = "Overall quality score")
    private BigDecimal overallQuality;

    @Schema(description = "Cleanliness score")
    private BigDecimal cleanliness;

    @Schema(description = "Staff kindness score")
    private BigDecimal staffKindness;

    @Schema(description = "Accessibility score")
    private BigDecimal accessibility;

    @Schema(description = "List of tag identifiers or names")
    private List<TagUpdateModel> tags;
}
