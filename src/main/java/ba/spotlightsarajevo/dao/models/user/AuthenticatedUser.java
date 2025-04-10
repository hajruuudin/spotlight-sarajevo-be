package ba.spotlightsarajevo.dao.models.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "Properties for an authenticated user with a System account")
public class AuthenticatedUser implements Serializable {
    @Serial
    private static final long versionUUId = 1L;

    @Schema(description = "First name of the user")
    private String firstName;

    @Schema(description = "Last name of the user")
    private String lastName;

    @Schema(description = "Email of the user")
    private String email;

    @Schema(description = "Image URL of the user")
    private String imageUrl;

    @Schema(description = "Admin status of the user")
    private Boolean isAdmin;

    @Schema(description = "Subscription status of the user")
    private Boolean isPremium;

}
