package ba.spotlightsarajevo.dao.models.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "Model for all properties of a Category Entity")
public class CategoryModel implements Serializable {
    @Serial
    private static final long versionUUId = 1L;

    @Schema(description = "Unique identifier of the category")
    private Integer id;

    @Schema(description = "Name of the category")
    private String categoryName;

    @Schema(description = "Description of the category")
    private String categoryDescription;
}
