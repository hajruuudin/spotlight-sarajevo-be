package ba.spotlightsarajevo.dao.models.spot;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SpotLocationModel implements Serializable {
    @Schema(description = "Slug of the spot")
    private String slug;

    @Schema(description = "Lattitude")
    private BigDecimal lattitude;

    @Schema(description = "Longitude")
    private BigDecimal longitude;

    @Schema(description = "Full name of the spot")
    private String officialName;

    @Schema(description = "Name of the spot category")
    private String categoryName;

    @Schema(description = "Image URL of the spot")
    private String imageUrl;
}
