package ba.spotlightsarajevo.dao.models.collection;


import ba.spotlightsarajevo.enums.ObjectType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "Properties for creating a collection")
public class CollectionCreateModel implements Serializable {
    @Serial
    private static final long versionUUId = 1L;

    @Schema(description = "Name of the collection")
    private String collectionName;

    @Schema(description = "The type of objects the collection takes in")
    private ObjectType collectionType;
}
