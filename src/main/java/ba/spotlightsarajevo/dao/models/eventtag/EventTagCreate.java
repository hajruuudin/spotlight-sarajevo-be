package ba.spotlightsarajevo.dao.models.eventtag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "Properties of an event tag create request")
public class EventTagCreate implements Serializable {
    @Serial
    public static final long versionUUId = 1L;

    @Schema(description = "Unique identifier of the event")
    private Integer eventId;

    @Schema(description = "Unique identifier of the tag")
    private Integer tagId;
}