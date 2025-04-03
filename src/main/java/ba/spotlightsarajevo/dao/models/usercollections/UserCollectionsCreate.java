package ba.spotlightsarajevo.dao.models.usercollections;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "Model for all properties of a user collection entity")
public class UserCollectionsCreate implements Serializable {
    @Serial
    private static final long versionUUId = 1L;

    @Schema(description = "Identifier of the user")
    private Integer userId;

    @Schema(description = "Identifier of the collection")
    private Integer collectionId;
}
