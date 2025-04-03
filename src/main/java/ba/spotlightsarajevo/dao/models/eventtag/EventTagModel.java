package ba.spotlightsarajevo.dao.models.eventtag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "Model for all properties of an event tag entity")
public class EventTagModel implements Serializable {
    @Serial
    public static final long versionUUId = 1L;

    @Schema(description = "Unique identifier of an event tag")
    private Integer id;

    @Schema(description = "Unique identifier of the event")
    private Integer eventId;

    @Schema(description = "Unique identifier of the tag")
    private Integer tagId;
}
