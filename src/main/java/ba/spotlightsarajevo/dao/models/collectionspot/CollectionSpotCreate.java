package ba.spotlightsarajevo.dao.models.collectionspot;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "Properties for adding a new spot inside a collection")
public class CollectionSpotCreate implements Serializable {
    @Serial
    private static final long versionUUId = 1L;

    @Schema(description = "Identifier of the collection")
    private Integer collectionId;

    @Schema(description = "Identifier of the actual spot")
    private Integer spotId;
}