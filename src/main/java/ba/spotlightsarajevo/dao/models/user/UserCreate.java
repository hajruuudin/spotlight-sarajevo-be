package ba.spotlightsarajevo.dao.models.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "Properties of a user entity create request")
public class UserCreate implements Serializable {
    @Serial
    private static final long versionUUId = 1L;

    @Schema(description = "Username")
    private String username;

    @Schema(description = "Email address")
    private String email;

    @Schema(description = "Password (hashed)")
    private String password;

    @Schema(description = "First name")
    private String firstName;

    @Schema(description = "Last name")
    private String lastName;

    @Schema(description = "Google identifier (if applicable)")
    private String googleId;

    @Schema(description = "Google email (if applicable)")
    private String googleEmail;

    @Schema(description = "URL of the Google profile picture (if applicable)")
    private String googlePictureUrl;

    @Schema(description = "User's locale")
    private String locale;

    @Schema(description = "Indicates if the user has a premium subscription")
    private Boolean isPremium;

    @Schema(description = "Type of registration (e.g., local, google)")
    private String registrationType;

    @Schema(description = "Indicates if the user is an administrator")
    private Boolean isAdmin;
}
