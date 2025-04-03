package ba.spotlightsarajevo.dao.models.userpreferences;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "Model for all properties of a user preferences entity")
public class UserPreferencesModel implements Serializable {
    @Serial
    private static final long versionUUId = 1L;

    @Schema(description = "Unique identifier")
    private Integer id;

    @Schema(description = "Identifier of the user")
    private Integer userId;

    @Schema(description = "Language preference")
    private String language;

    @Schema(description = "Identifier for user's favorites")
    private String favouritesId;

    @Schema(description = "Answer to question 01")
    private Boolean answer01;

    @Schema(description = "Answer to question 02")
    private Boolean answer02;

    @Schema(description = "Answer to question 03")
    private Boolean answer03;

    @Schema(description = "Answer to question 04")
    private Boolean answer04;
}
