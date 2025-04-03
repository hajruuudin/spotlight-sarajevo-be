package ba.spotlightsarajevo.dao.models.userfavouritecategories;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "Model for all properties of a user favourite categories model")
public class UserFavouriteCategoriesModel implements Serializable {
    @Serial
    public static final long versionUUId = 1L;

    @Schema(description = "Unique identifier")
    private Integer id;

    @Schema(description = "Identifier of the user")
    private Integer userId;

    @Schema(description = "Identifier of the category")
    private Integer categoryId;
}
