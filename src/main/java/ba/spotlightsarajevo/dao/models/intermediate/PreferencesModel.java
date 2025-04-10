package ba.spotlightsarajevo.dao.models.intermediate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "Properties of a model that is sent from the frontend regarding user preferences at register")
public class PreferencesModel implements Serializable {
    @Serial
    private static final long versionUUId = 1L;

    @Schema(description = "Answer to the first question")
    private Boolean answer01;

    @Schema(description = "Answer to the second question")
    private Boolean answer02;

    @Schema(description = "Answer to the third question")
    private Boolean answer03;

    @Schema(description = "Answer to the fourth question")
    private Boolean answer04;

    @Schema(description = "Language preference of the user")
    private String language;

    @Schema(description = "Identifier of the first selected category")
    private Integer category_id_1;

    @Schema(description = "Identifier of the second selected category")
    private Integer category_id_2;

    @Schema(description = "Identifier of the third selected category")
    private Integer category_id_3;
}
