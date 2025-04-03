package ba.spotlightsarajevo.dao.models.spotworkhours;

import ba.spotlightsarajevo.enums.WeekDay;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;

@Data
@Schema(description = "Model for all properties of a spot work hours entity")
public class SpotWorkHoursModel implements Serializable{
    @Serial
    private static final long versionUUId = 1L;

    @Schema(description = "Unique identifier")
    private Integer id;

    @Schema(description = "Day of the week")
    private WeekDay day;

    @Schema(description = "Start time")
    private LocalTime startTime;

    @Schema(description = "End time")
    private LocalTime endTime;

    @Schema(description = "Identifier of the spot")
    private Integer spotId;
}
