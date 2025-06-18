package ba.spotlightsarajevo.dao.models.spot;

import ba.spotlightsarajevo.utils.ObjectShorthand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "Properties for a shorthand spot entity, used wherever we have spot search results")
public class SpotShorthand implements Serializable, ObjectShorthand {
    @Serial
    private static final Long versionUUid = 1L;

    @Schema(description = "Unique identifier")
    private Integer id;

    @Schema(description = "URL-friendly identifier")
    private String slug;

    @Schema(description = "Official name")
    private String officialName;

    @Schema(description = "Short description")
    private String smallDescription;

    @Schema(description = "Name of the category")
    private String categoryName;

    @Schema(description = "Tags of the spot")
    private List<String> tagNames;

    @Schema(description = "Overall rating of the Spot")
    private BigDecimal rating;

    @Schema(description = "Image URL of the category")
    private String imageUrl;

    @Schema(description = "Date of creation")
    private LocalDateTime created;

    @Schema(description = "Date of last modification")
    private LocalDateTime modified;

}
