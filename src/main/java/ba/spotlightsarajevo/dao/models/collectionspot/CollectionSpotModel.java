package ba.spotlightsarajevo.dao.models.collectionspot;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "Model for all properties of a collection spot")
public class CollectionSpotModel implements Serializable {
    @Serial
    private static final long versionUUId = 1L;

    @Schema(description = "Unique identifier of the collection spot entity")
    private Integer id;

    @Schema(description = "Identifier of the collection")
    private Integer collectionId;

    @Schema(description = "Identifier of the actual spot")
    private Integer spotId;
}
