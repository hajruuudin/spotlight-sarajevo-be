package ba.spotlightsarajevo.dao.models.collectionevent;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "Properties for adding a new event inside a collection")
public class CollectionEventCreate implements Serializable {
    @Serial
    private static final long versionUUId = 1L;

    @Schema(description = "Identifier of the collection")
    private Integer collectionId;

    @Schema(description = "Identifier of the actual event")
    private Integer eventId;
}