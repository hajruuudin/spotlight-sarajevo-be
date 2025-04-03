package ba.spotlightsarajevo.dao.models.collectionevent;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "Model for all properties of a collection event")
public class CollectionEventModel implements Serializable {
    @Serial
    private static final long versionUUId = 1L;

    @Schema(description = "Unique identifier of the collection event")
    private Integer id;

    @Schema(description = "Identifier of the collection")
    private Integer collectionId;

    @Schema(description = "Identifier of the actual event")
    private Integer eventId;
}
