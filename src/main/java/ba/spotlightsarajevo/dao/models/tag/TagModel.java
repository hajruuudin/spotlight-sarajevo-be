package ba.spotlightsarajevo.dao.models.tag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "Model for all properties of a tag entity")
public class TagModel implements Serializable {
    @Serial
    private static final long versionUUId = 1L;

    @Schema(description = "Unique identifier of the tag")
    private Integer id;

    @Schema(description = "Name of the tag")
    private String tagName;

    @Schema(description = "Description of the tag")
    private String tagDescription;
}
