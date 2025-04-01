package ba.spotlightsarajevo.dao.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Schema(description = "Basic, all arguments model of a Sample Entity")
public class SampleModel implements Serializable {
    @Serial
    private static final long versionUUID = 1L;

    @Schema(description = "Unique identifier of the Sample")
    private Long id;

    @Schema(description = "Name of the Sample")
    private String name;

    @Schema(description = "Date of the Sample")
    private LocalDate date;
}
