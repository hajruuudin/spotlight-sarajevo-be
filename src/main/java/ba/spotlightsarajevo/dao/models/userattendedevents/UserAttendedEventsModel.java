package ba.spotlightsarajevo.dao.models.userattendedevents;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Schema(description = "Model for all properties of a user attended events entity")
public class UserAttendedEventsModel implements Serializable {
    @Serial
    private static final long versionUUId = 1L;

    @Schema(description = "Unique identifier")
    private Integer id;

    @Schema(description = "Identifier of the user")
    private Integer userId;

    @Schema(description = "Identifier of the event")
    private Integer eventId;

    @Schema(description = "Timestamp when the record was added")
    private LocalDateTime addedAt;
}
