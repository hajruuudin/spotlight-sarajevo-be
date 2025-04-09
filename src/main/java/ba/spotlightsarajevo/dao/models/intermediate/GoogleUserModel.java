package ba.spotlightsarajevo.dao.models.intermediate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "Model for Google user information")
public class GoogleUserModel implements Serializable {
    @Serial
    private static final long versionUUId = 1L;

    @Schema(description = "Google's unique identifier for the user")
    private String googleId;

    @Schema(description = "URL of the user's Google profile picture")
    private String googlePictureUrl;

    @Schema(description = "User's first name from Google")
    private String firstName;

    @Schema(description = "User's last name from Google")
    private String lastName;

    @Schema(description = "User's locale from Google (may be null)")
    private String locale;

    @Schema(description = "User's email address from Google")
    private String email;
}
