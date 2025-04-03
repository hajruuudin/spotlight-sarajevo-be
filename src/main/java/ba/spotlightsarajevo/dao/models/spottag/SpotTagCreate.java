package ba.spotlightsarajevo.dao.models.spottag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "Properties of a spot tag entity create request")
public class SpotTagCreate implements Serializable {
    @Serial
    private static final long versionUUId = 1L;

    @Schema(description = "Identifier of the spot")
    private Integer spotId;

    @Schema(description = "Identifier of the tag")
    private Integer tagId;
}
