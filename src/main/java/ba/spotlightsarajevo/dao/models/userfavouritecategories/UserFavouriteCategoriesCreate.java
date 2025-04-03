package ba.spotlightsarajevo.dao.models.userfavouritecategories;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "Properties for a user favourites category create request")
public class UserFavouriteCategoriesCreate implements Serializable {
    @Serial
    private static final long versionUUId = 1L;

    @Schema(description = "Identifier of the user")
    private Integer userId;

    @Schema(description = "Identifier of the category")
    private Integer categoryId;
}
