package ba.spotlightsarajevo.dao.models.collection;

import ba.spotlightsarajevo.enums.ObjectType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "Basic, all arguments model of a Collection ")
public class CollectionModel implements Serializable {
    @Serial
    private static final long versionUUId = 1L;

    @Schema(description = "Unique identifier of the collection")
    private Integer id;

    @Schema(description = "Name of the collection")
    private String collectionName;

    @Schema(description = "The type of objects the collection takes in")
    private ObjectType collectionType;

    @Schema(description = "The user the collection is assocated with")
    private Integer userId;
}
