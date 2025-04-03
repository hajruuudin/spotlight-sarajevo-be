package ba.spotlightsarajevo.dao.models.uservisitedspots;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Schema(description = "Model for all properties of a user visited spot entity")
public class UserVisitedSpotsCreate implements Serializable {
    @Serial
    private static final long versionUUId = 1L;

    @Schema(description = "Identifier of the user")
    private Integer userId;

    @Schema(description = "Identifier of the spot")
    private Integer spotId;

    @Schema(description = "Timestamp when the record was added")
    private LocalDateTime addedAt;
}
