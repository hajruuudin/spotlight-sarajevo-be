package ba.spotlightsarajevo.dao.models.collection;

import ba.spotlightsarajevo.enums.ObjectType;
import ba.spotlightsarajevo.utils.ObjectShorthand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Schema(description = "Properties of a collection model with its shorthand response")
public class CollectionWithItemsModel implements Serializable {
    @Serial
    private static final Long versionUUId = 1L;

    @Schema(description = "Id of the collection")
    private Integer id;

    @Schema(description = "Name of the collection")
    private String collectionName;

    @Schema(description = "Type of the collection, event or spot")
    private ObjectType collectionType;

    @Schema(description = "A list of the item shorthands inside the collection")
    private List<ObjectShorthand> collectionItems;

}
