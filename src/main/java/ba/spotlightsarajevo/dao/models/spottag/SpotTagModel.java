package ba.spotlightsarajevo.dao.models.spottag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "Model for all properties of a spot tag entity")
public class SpotTagModel implements Serializable {
    @Serial
    private static final long versionUUId = 1L;

    @Schema(description = "Unique identifier")
    private Integer id;

    @Schema(description = "Identifier of the spot")
    private Integer spotId;

    @Schema(description = "Identifier of the tag")
    private Integer tagId;
}
