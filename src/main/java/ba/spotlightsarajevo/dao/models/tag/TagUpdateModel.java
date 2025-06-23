package ba.spotlightsarajevo.dao.models.tag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "Properties used while updating a tag")
public class TagUpdateModel implements Serializable {
    @Schema(description = "ID of the tag")
    private Integer tagId;

    @Schema(description = "Name of the tag")
    private String tagName;
}
