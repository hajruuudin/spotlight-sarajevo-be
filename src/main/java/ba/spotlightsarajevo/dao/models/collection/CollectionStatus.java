package ba.spotlightsarajevo.dao.models.collection;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "Gives the name of the collection along with the presence of a specific spot within a collection")
public class CollectionStatus implements Serializable {
    @Serial
    private static final Long versionUUId = 1L;

    @Schema(description = "The name of the collection")
    private String collectionName;

    @Schema(description = "Presence of the item within the collection (true or false)")
    private Boolean itemStatus;
}
