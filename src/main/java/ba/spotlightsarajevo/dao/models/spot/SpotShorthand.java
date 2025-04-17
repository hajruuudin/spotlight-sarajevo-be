package ba.spotlightsarajevo.dao.models.spot;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Schema(description = "Properties for a shorthand spot entity, used wherever we have spot search results")
public class SpotShorthand implements Serializable {
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

    @Schema(description = "Image URL of the category")
    private String imageUrl;

}
